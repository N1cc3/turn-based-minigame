package ui.ingame

import game.data.GameMap
import hexgrid.Hex
import scalafx.scene.canvas.Canvas


class GameCanvas extends Canvas {

	width = 640
	height = 480

	val hexSize = 150

	def drawMap(map: GameMap) = {
		for (y <- 0 until map.sizeY) {
			for (x <- 0 until map.sizeX) {
				val hex = new Hex(x, y)
				val position = hex.drawingPosition(hexSize)
				this.getGraphicsContext2D.drawImage(map.terrain(x)(y).image, position.x, position.y, hexSize, hexSize)
			}
		}
	}

}
