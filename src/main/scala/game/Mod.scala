package game

import java.io.File

import com.github.tototoshi.csv.CSVReader
import game.data._
import hexgrid.Hex
import scalafx.scene.image.Image

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

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
		terrainTypes += name -> new TerrainType(name, moveCost, effects, image)
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
		val scenario = new Map(name, sizeX, sizeY, description)

		val terrainReader: CSVReader = CSVReader.open(path + "terrain.csv")
		for (terrainData <- terrainReader.allWithHeaders()) {
			val x = terrainData("X").toInt
			val y = terrainData("Y").toInt
			val terrain = terrainData("TERRAIN_TYPE")
			scenario.terrain(x)(y) = terrainTypes(terrain)
		}

		val players = new ListBuffer[Player]
		players += new Player()
		players += new Player()

		val gameState = new GameState(scenario, players)

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


	private def getImage(name: String) = new Image("file://" + modPath + name)

	private def getScenarios: Seq[String] = {
		val d = new File(modPath + "/scenarios")
		if (d.exists && d.isDirectory) {
			d.listFiles.filter(_.isDirectory).map(_.getName)
		} else {
			List[String]()
		}
	}

}
