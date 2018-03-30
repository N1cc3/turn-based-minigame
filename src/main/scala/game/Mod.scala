package game

import com.github.tototoshi.csv.CSVReader
import game.data.{Effect, EffectType, Map, TerrainType, UnitType}
import scalafx.scene.image.Image
import ui.Theme

import scala.collection.mutable

class Mod(val name: String) {

	val modPath = getClass.getResource("/mods/" + name).getPath + "/"

	val theme = new Theme(modPath + "theme.csv")

	// Terrain

	val terrainTypes = new mutable.HashMap[String, TerrainType]
	val terrainReader: CSVReader = CSVReader.open(modPath + "terrains.csv")
	for (terrainData <- terrainReader.allWithHeaders()) {
		val name = terrainData("NAME")
		val moveCost = terrainData("MOVE_COST").toInt
		val effects = List.empty[Effect]
		val image = getImage(terrainData("IMAGE"))
		terrainTypes += name -> new TerrainType(name, moveCost, effects, image)
	}

	// Maps

	val maps = new mutable.HashMap[String, Map]
	val mapsReader: CSVReader = CSVReader.open(modPath + "maps.csv")
	for (mapsData <- mapsReader.allWithHeaders()) {
		val name = mapsData("NAME")
		val sizeX = mapsData("SIZE_X").toInt
		val sizeY = mapsData("SIZE_Y").toInt
		val map = new Map(name, sizeX, sizeY)
		maps += name -> map

		val mapReader: CSVReader = CSVReader.open(modPath + "maps/" + name + ".csv")
		for (mapData <- mapReader.allWithHeaders()) {
			val x = mapData("X").toInt
			val y = mapData("Y").toInt
			val terrain = mapData("TERRAIN")
			map.terrain(x)(y) = terrainTypes(terrain)
		}
	}

	// Units

	val unitTypes = new mutable.HashMap[String, UnitType]
	val unitReader: CSVReader = CSVReader.open(modPath + "units.csv")
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

	// Effects

	val effectTypes = new mutable.HashMap[String, EffectType]
	val effectReader: CSVReader = CSVReader.open(modPath + "effects.csv")
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


	def getImage(name: String) = new Image("file://" + modPath + name)

}
