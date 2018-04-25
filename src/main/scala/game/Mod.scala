package game

import java.io.{File, FileOutputStream, PrintWriter}

import com.github.tototoshi.csv.{CSVReader, CSVWriter}
import game.data._
import hexgrid.Hex
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

import scala.collection.mutable
import scala.reflect.io.Directory

class Mod(val name: String) {

	val modPath: String = getClass.getResource("/mods/" + name).getPath + "/"

	val cssFilePath: String = "file://" + modPath + "theme.css"

	val scenarioNames: Seq[String] = getScenarios

	// Effects

	val effectTypes = new mutable.HashMap[String, EffectType]
	val effectReader: CSVReader = CSVReader.open(modPath + "effectTypes.csv")
	for (effectData <- effectReader.allWithHeaders()) {
		val name = effectData("NAME")
		val duration = effectData("DURATION").toInt
		val attack = effectData("ATTACK").toInt
		val defence = effectData("DEFENCE").toInt
		val armor = effectData("ARMOR").toInt
		val range = effectData("RANGE").toInt
		val speed = effectData("SPEED").toInt
		val damage = effectData("DAMAGE").toInt
		effectTypes += name -> new EffectType(name, duration, attack, defence, armor, range, speed, damage)
	}

	// Terrain

	val terrainTypes = new mutable.HashMap[String, TerrainType]
	val terrainTypeReader: CSVReader = CSVReader.open(modPath + "terrainTypes.csv")
	for (terrainTypeData <- terrainTypeReader.allWithHeaders()) {
		val name = terrainTypeData("NAME")
		val moveCost = terrainTypeData("MOVE_COST").toInt
		val effects = List.empty[Effect]
		val image = getImage(terrainTypeData("IMAGE"))
		val color = terrainTypeData("COLOR")
		terrainTypes += name -> new TerrainType(name, moveCost, effects, image, color)
	}

	// Units

	val unitTypes = new mutable.HashMap[String, UnitType]
	val unitReader: CSVReader = CSVReader.open(modPath + "unitTypes.csv")
	for (unitData <- unitReader.allWithHeaders()) {
		val name = unitData("NAME")
		val hp = unitData("HP").toInt
		val attack = unitData("ATTACK").toInt
		val defence = unitData("DEFENCE").toInt
		val armor = unitData("ARMOR").toInt
		val range = unitData("RANGE").toInt
		val speed = unitData("SPEED").toInt
		val cost = unitData("COST").toInt
		val upkeep = unitData("UPKEEP").toInt
		val image = getImage(unitData("IMAGE"))
		unitTypes += name -> new UnitType(name, hp, attack, defence, armor, range, speed, cost, upkeep, image)
	}


	def loadScenario(name: String): GameState = {
		loadGame(modPath + "scenarios/" + name + "/")
	}

	def loadGame(path: String): GameState = {
		val scenarioReader: CSVReader = CSVReader.open(path + "data.csv")
		val scenarioData = scenarioReader.allWithHeaders().head
		val name = scenarioData("NAME")
		val sizeX = scenarioData("SIZE_X").toInt
		val sizeY = scenarioData("SIZE_Y").toInt
		val description = scenarioData("DESCRIPTION")
		val terrain = new Terrain(name, sizeX, sizeY, description)

		val terrainReader: CSVReader = CSVReader.open(path + "terrain.csv")
		for (terrainData <- terrainReader.allWithHeaders()) {
			val x = terrainData("X").toInt
			val y = terrainData("Y").toInt
			val terrainType = terrainTypes(terrainData("TERRAIN_TYPE"))
			terrain(x, y) = terrainType
		}

		val players = Array(new Player(Color.Blue), new Player(Color.Red))

		val gameState = new GameState(terrain, players)

		val unitReader: CSVReader = CSVReader.open(path + "units.csv")
		for (unitData <- unitReader.allWithHeaders()) {
			val x = unitData("X").toInt
			val y = unitData("Y").toInt
			val unitType = unitData("UNIT_TYPE")
			val player = unitData("PLAYER").toInt
			val unit = new Unit(unitTypes(unitType), players(player))
			unit.position = new Hex(x, y)
			gameState.units += unit
		}

		gameState
	}

	def saveGame(gameState: GameState) {
		createSaveFolder()
		val savePath: String = getSavePath

		val dataFile = new File(savePath + "data.csv")
		val scenarioWriter: CSVWriter = CSVWriter.open(dataFile)
		scenarioWriter.writeRow(List("NAME","SIZE_X","SIZE_Y","DESCRIPTION"))
		val terrain = gameState.terrain
		val scenarioName = terrain.name
		val scenarioSizeX = terrain.sizeX
		val scenarioSizeY = terrain.sizeY
		val scenarioDescription = terrain.description
		scenarioWriter.writeRow(List(scenarioName, scenarioSizeX, scenarioSizeY, scenarioDescription))
		scenarioWriter.close()

		val terrainFile = new File(savePath + "terrain.csv")
		val terrainWriter: CSVWriter = CSVWriter.open(terrainFile)
		terrainWriter.writeRow(List("X","Y","TERRAIN_TYPE"))
		for (x <- 0 until scenarioSizeX) {
			for (y <- 0 until scenarioSizeY) {
				terrainWriter.writeRow(List(x, y, terrain(x, y).name))
			}
		}
		terrainWriter.close()

		val unitFile = new File(savePath + "units.csv")
		val unitWriter: CSVWriter = CSVWriter.open(unitFile)
		unitWriter.writeRow(List("X","Y","UNIT_TYPE","PLAYER"))
		for (unit <- gameState.units) {
			val x = unit.position.x
			val y = unit.position.y
			val unitType = unit.unitType.name
			val player = gameState.players.indexOf(unit.player)
			unitWriter.writeRow(List(x, y, unitType, player))
		}
		unitWriter.close()
	}

	def getSavePath: String = "saves/" + this.name + "/"

	// Helpers

	def getImage(name: String) = new Image("file://" + modPath + name)

	private def getScenarios: Seq[String] = {
		val d = new File(modPath + "/scenarios")
		if (d.exists && d.isDirectory) {
			d.listFiles.filter(_.isDirectory).map(_.getName)
		} else {
			List[String]()
		}
	}

	private def createSaveFolder() {
		val saves = new File("saves")
		saves.setWritable(true)
		val savesDirectory = new Directory(saves)
		savesDirectory.createDirectory()

		val modSave = new File("saves/" + this.name)
		modSave.setWritable(true)
		val modSaveDirectory = new Directory(modSave)
		modSaveDirectory.createDirectory()
	}

}
