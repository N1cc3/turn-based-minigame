package ui.ingame

import game.data.{Map, Unit}
import hexgrid.Hex
import scalafx.geometry.Point2D
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

import scala.math.{min, sqrt}


class GameCanvas extends Canvas {

	private val gc = this.getGraphicsContext2D

	def hexSize(map: Map): Double = {
		val x: Double = this.width.value / (map.sizeX.toDouble + 0.5) / sqrt(3)
		val y: Double = this.height.value / (map.sizeY.toDouble * 3.0 / 4.0 + 1.0 / 4.0) / 2
		min(x, y)
	}

	def drawMap(map: Map) {
		val size = hexSize(map)
		gc.clearRect(0, 0, this.getWidth, this.getHeight)
		for (y <- 0 until map.sizeY) {
			for (x <- 0 until map.sizeX) {
				val hex = new Hex(x, y)
				val position = hex.drawingPosition(size)
				gc.drawImage(map.terrain(x)(y).image, position.x, position.y, size * sqrt(3), size * 2)
			}
		}
	}

	def drawUnits(units: List[Unit], map: Map) {
		val size = hexSize(map)
		for (unit <- units) {
			val position = unit.position.drawingPosition(size)
			gc.drawImage(unit.unitType.image, position.x, position.y, size * sqrt(3), size * 2)
		}
	}

	def drawSelection(map: Map, hex: Hex) {
		val size = hexSize(map)
		val position = hex.drawingPosition(size)
		val center = new Point2D(position.x + size * sqrt(3) / 2, position.y + size * 2 / 2)
		for (i <- 0 until 6) {
			val corner1 = hex.corner(center, size, i)
			val corner2 = hex.corner(center, size, i + 1 % 6)
			gc.setFill(Color.Blue)
			gc.setStroke(Color.Blue)
			gc.setLineWidth(size / 20)
			gc.strokeLine(corner1.x, corner1.y, corner2.x, corner2.y)
		}
	}

}
