package ui.ingame

import game.data.Map
import hexgrid.Hex
import scalafx.scene.canvas.Canvas

import scala.math.min


class GameCanvas extends Canvas {

	var map: Map = _

	private val gc = this.getGraphicsContext2D

	def hexSize: Double = {
		val x: Double = this.width.value / (map.sizeX.toDouble + 0.5)
		val y: Double = this.height.value / (map.sizeY.toDouble * 3.0 / 4.0 + 1.0 / 4.0)
		min(x, y)
	}

	def drawMap() = {
		gc.clearRect(0, 0, this.getWidth, this.getHeight)
		for (y <- 0 until map.sizeY) {
			for (x <- 0 until map.sizeX) {
				val hex = new Hex(x, y)
				val position = hex.drawingPosition(hexSize)
				gc.drawImage(map.terrain(x)(y).image, position.x, position.y, hexSize, hexSize)
			}
		}
	}

}
