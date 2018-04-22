package ui.ingame

import game.data.{GameState, Terrain, Unit}
import hexgrid.Hex
import scalafx.scene.canvas.Canvas
import scalafx.scene.effect.ColorAdjust
import scalafx.scene.paint.Color

import scala.math.{min, sqrt}


class GameCanvas extends Canvas {

	private val gc = this.getGraphicsContext2D

	def drawGame(gameState: GameState) {
		this.drawMap(gameState.terrain)
		this.drawUnits(gameState.units.toList, gameState.terrain)
		gameState.players.foreach(player => {
			player.selection.foreach(selectedHex => {
				gameState.units.foreach(unit => {
					if (unit.position.equals(selectedHex)) {
						this.drawMoveHexes(gameState.terrain, unit)
						if (unit.canAttack) this.drawAttackHexes(gameState.terrain, unit)
					}
				})
				this.drawSelection(gameState.terrain, selectedHex, player.color)
			})
			this.drawCursor(gameState.terrain, player.cursor, player.color)
		})
	}

	private def hexSize(terrain: Terrain): Double = {
		val x: Double = this.width.value / (terrain.sizeX.toDouble + 0.5) / sqrt(3)
		val y: Double = this.height.value / (terrain.sizeY.toDouble * 3.0 / 4.0 + 1.0 / 4.0) / 2
		min(x, y)
	}

	private def drawMap(terrain: Terrain) {
		val size = hexSize(terrain)
		gc.clearRect(0, 0, this.getWidth, this.getHeight)
		val columnShadowEffect = new ColorAdjust()
		columnShadowEffect.setBrightness(-0.05)
		for (y <- 0 until terrain.sizeY) {
			for (x <- 0 until terrain.sizeX) {
				val hex = new Hex(x, y)
				val position = hex.drawingPosition(size)
				if (hex.x % 2 == 1) gc.setEffect(columnShadowEffect)
				gc.drawImage(terrain(x, y).image, position.x, position.y, size * sqrt(3), size * 2)
				if (hex.x % 2 == 1) gc.setEffect(null)
			}
		}
	}

	private def drawUnits(units: List[Unit], terrain: Terrain) {
		val size = hexSize(terrain)
		val redPlayerEffect = new ColorAdjust()
		redPlayerEffect.hue = 0.85
		for (unit <- units) {
			val position = unit.position.drawingPosition(size)
			if (unit.player.color == Color.Red) gc.setEffect(redPlayerEffect)
			gc.drawImage(unit.unitType.image, position.x, position.y, size * sqrt(3), size * 2)
			if (unit.player.color == Color.Red) gc.setEffect(null)
		}
	}

	private def drawCursor(terrain: Terrain, hex: Hex, color: Color) {
		val size = hexSize(terrain)
		val corners = hex.corners(size)
		gc.setStroke(color.opacity(0.5))
		for (i <- 0 until 6) {
			gc.setLineWidth(size / 40)
			gc.strokeLine(corners(i).x, corners(i).y, corners((i + 1) % 6).x, corners((i + 1) % 6).y)
		}
	}

	private def drawSelection(terrain: Terrain, hex: Hex, color: Color) {
		val size = hexSize(terrain)
		val corners = hex.corners(size)
		gc.setStroke(color.opacity(0.5))
		for (i <- 0 until 6) {
			gc.setLineWidth(size / 20)
			gc.strokeLine(corners(i).x, corners(i).y, corners((i + 1) % 6).x, corners((i + 1) % 6).y)
		}
		gc.setFill(color.opacity(0.1))
		gc.fillPolygon(corners.map(_.x), corners.map(_.y), 6)
	}

	private def drawMoveHexes(terrain: Terrain, unit: Unit) {
		val size = hexSize(terrain)
		gc.setStroke(Color.rgb(0, 255, 0).opacity(0.5))
		val moveHexes = unit.getMoveHexes(terrain)
		moveHexes.foreach(hex => {
			val corners = hex.corners(size)
			val neighbors = hex.neighbors()
			for (i <- 0 until 6) {
				val neighbor = neighbors(5 - i)
				if (!moveHexes.contains(neighbor) && unit.position != neighbor) {
					gc.setLineWidth(size / 20)
					gc.strokeLine(corners(i).x, corners(i).y, corners((i + 1) % 6).x, corners((i + 1) % 6).y)
				}
			}
		})
	}

	private def drawAttackHexes(terrain: Terrain, unit: Unit) {
		val size = hexSize(terrain)
		gc.setStroke(Color.rgb(255, 0, 0).opacity(0.5))
		val attackHexes = unit.getAttackHexes(terrain)
		attackHexes.foreach(hex => {
			val corners = hex.corners(size)
			val neighbors = hex.neighbors()
			for (i <- 0 until 6) {
				val neighbor = neighbors(5 - i)
				if (!attackHexes.contains(neighbor) && unit.position != neighbor) {
					gc.setLineWidth(size / 40)
					gc.strokeLine(corners(i).x, corners(i).y, corners((i + 1) % 6).x, corners((i + 1) % 6).y)
				}
			}
		})
	}

}
