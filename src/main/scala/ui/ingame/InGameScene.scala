package ui.ingame

import game.Mod
import game.data.{GameState, Player}
import hexgrid.Hex
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.scene.layout.{HBox, VBox}

import scala.math.{max, min}

class InGameScene(mod: Mod) extends Scene {

	private val mainBox = new HBox()
	this.content.add(mainBox)
	mainBox.styleClass += "InGameScene-mainBox"

	private val playerPanel1 = new PlayerPanel
	playerPanel1.styleClass += "InGameScene-playerPanel1"
	mainBox.getChildren.add(playerPanel1)

	private val canvasBox = new VBox()
	canvasBox.styleClass += "InGameScene-canvasBox"
	mainBox.getChildren.add(canvasBox)

	val canvas = new GameCanvas()
	val gameState: GameState = mod.loadScenario(mod.scenarioNames.head)
	canvasBox.getChildren.add(canvas)

	private val playerPanel2 = new PlayerPanel
	playerPanel2.styleClass += "InGameScene-playerPanel2"
	mainBox.getChildren.add(playerPanel2)

	canvas.drawGame(gameState)

	// Listeners

	this.widthProperty.addListener((_, _, newValue) => {
		canvas.width = newValue.doubleValue() - playerPanel1.getWidth - playerPanel2.getWidth
		canvas.drawGame(gameState)
	})

	this.heightProperty.addListener((_, _, newValue) => {
		canvas.height = newValue.doubleValue()
		canvas.drawGame(gameState)
	})

	this.setOnKeyPressed(ke => {
		val player1 = gameState.players.head
		val player2 = gameState.players(1)
		val keyCode = ke.getCode
		keyCode match {
			// Player 1
			case KeyCode.W.delegate => moveCursor(player1, 0, -1)
			case KeyCode.A.delegate => moveCursor(player1, -1, 0)
			case KeyCode.S.delegate => moveCursor(player1, 0, 1)
			case KeyCode.D.delegate => moveCursor(player1, 1, 0)
			case KeyCode.Digit1.delegate => select(player1)
			case KeyCode.Q.delegate => cancel(player1)

			// Player 2
			case KeyCode.Up.delegate => moveCursor(player2, 0, -1)
			case KeyCode.Left.delegate => moveCursor(player2, -1, 0)
			case KeyCode.Down.delegate => moveCursor(player2, 0, 1)
			case KeyCode.Right.delegate => moveCursor(player2, 1, 0)
			case KeyCode.Control.delegate => select(player2)
			case KeyCode.Shift.delegate => cancel(player2)

			case _ =>
		}

		player1.cursor.foreach(cursor => {
			playerPanel1.terrainInfo.show(gameState.map.terrain(cursor.x)(cursor.y))
			playerPanel1.unitInfo.clear()
			gameState.units.foreach(unit => if (unit.position.equals(cursor)) playerPanel1.unitInfo.show(unit))
		})
		player2.cursor.foreach(cursor => {
			playerPanel2.terrainInfo.show(gameState.map.terrain(cursor.x)(cursor.y))
			playerPanel2.unitInfo.clear()
			gameState.units.foreach(unit => if (unit.position.equals(cursor)) playerPanel2.unitInfo.show(unit))
		})
		canvas.drawGame(gameState)
	})

	// Player actions

	var state: UiState = NoSelection

	private def moveCursor(player: Player, moveX: Int, moveY: Int) {
		if (player.cursor.isDefined) {
			val x = min(gameState.map.sizeX - 1, max(0, player.cursor.get.x + moveX))
			val y = min(gameState.map.sizeY - 1, max(0, player.cursor.get.y + moveY))
			player.cursor = Option(new Hex(x, y))
		} else {
			player.cursor = Option(new Hex(0, 0))
		}
	}

	private def select(player: Player) {
		state match {
			case NoSelection => {
				player.selection = player.cursor
				state = Selection
			}
			case Selection =>
		}
	}

	private def cancel(player: Player) {
		state match {
			case NoSelection =>
			case Selection => {
				player.selection = None
				state = NoSelection
			}
		}
	}

	sealed trait UiState

	case object NoSelection extends UiState

	case object Selection extends UiState

	case object Moving extends UiState

	case object Attacking extends UiState

}