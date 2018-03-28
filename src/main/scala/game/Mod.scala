package game

import com.github.tototoshi.csv.CSVReader
import game.data.{Effect, Map, TerrainType}
import scalafx.scene.image.Image
import ui.Theme

import scala.collection.mutable

class Mod(val name: String) {

	val modPath = getClass.getResource("/mods/" + name).getPath + "/"

	val theme = new Theme(modPath + "theme.csv")

	val terrainTypes = new mutable.HashMap[String, TerrainType]

	val terrainReader: CSVReader = CSVReader.open(modPath + "terrains.csv")
	for (terrainData <- terrainReader.allWithHeaders()) {
		val name = terrainData("NAME")
		val moveCost = terrainData("MOVE_COST").toInt
		val effects = List.empty[Effect]
		val image = getImage(terrainData("IMAGE"))
		terrainTypes += name -> new TerrainType(name, moveCost, effects, image)
	}

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

	def getImage(name: String) = new Image("file://" + modPath + name)

}
