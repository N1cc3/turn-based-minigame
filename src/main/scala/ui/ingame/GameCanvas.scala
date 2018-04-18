package ui.ingame

import game.data.{GameState, Map, Unit}
import hexgrid.Hex
import scalafx.geometry.Point2D
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

import scala.math.{min, sqrt}


class GameCanvas extends Canvas {

	private val gc = this.getGraphicsContext2D

	def drawGame(gameState: GameState) {
		this.drawMap(gameState.map)
		this.drawUnits(gameState.units.toList, gameState.map)
		gameState.players.foreach(player => player.cursor.foreach(cursor => this.drawSelection(gameState.map, cursor, player.color)))
	}

	def hexSize(map: Map): Double = {
		val x: Double = this.width.value / (map.sizeX.toDouble + 0.5) / sqrt(3)
		val y: Double = this.height.value / (map.sizeY.toDouble * 3.0 / 4.0 + 1.0 / 4.0) / 2
		min(x, y)
	}

	private def drawMap(map: Map) {
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

	private def drawUnits(units: List[Unit], map: Map) {
		val size = hexSize(map)
		for (unit <- units) {
			val position = unit.position.drawingPosition(size)
			gc.drawImage(unit.unitType.image, position.x, position.y, size * sqrt(3), size * 2)
		}
	}

	private def drawSelection(map: Map, hex: Hex, color: Color) {
		val size = hexSize(map)
		val position = hex.drawingPosition(size)
		val center = new Point2D(position.x + size * sqrt(3) / 2, position.y + size * 2 / 2)
		val corners = hex.corners(center, size)
		gc.setStroke(color.opacity(0.5))
		for (i <- 0 until 6) {
			gc.setLineWidth(size / 20)
			gc.strokeLine(corners(i).x, corners(i).y, corners((i + 1) % 6).x, corners((i + 1) % 6).y)
		}
		gc.setFill(color.opacity(0.1))
		gc.fillPolygon(corners.map(_.x), corners.map(_.y), 6)
	}

}
