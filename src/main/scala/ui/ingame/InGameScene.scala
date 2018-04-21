package ui.ingame

import game.Mod
import game.data.{GameState, Player}
import hexgrid.Hex
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}
import ui.{Command, Controls}

import scala.collection.mutable
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

	private val playerPanel2 = new PlayerPanel
	playerPanel2.styleClass += "InGameScene-playerPanel2"
	mainBox.getChildren.add(playerPanel2)

	val playerPanels = Array(playerPanel1, playerPanel2)

	val canvas = new GameCanvas()
	val gameState: GameState = mod.loadScenario(mod.scenarioNames.head)
	canvasBox.getChildren.add(canvas)

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

	// Player actions

	private val uiState = mutable.Map[Player, UiState.Value]().empty
	gameState.players.foreach(uiState += _ -> UiState.NoSelection)

	val controls = new Controls

	this.setOnKeyPressed(ke => {

		val input: Option[(Int, Command.Value)] = controls.get(ke.getCode.getName)

		input.foreach(validInput => {
			val playerNumber: Int = validInput._1
			val player: Player = gameState.players(playerNumber)
			val command: Command.Value = validInput._2

			command match {
				case Command.Up => moveCursor(player, 0, -1)
				case Command.Left => moveCursor(player, -1, 0)
				case Command.Down => moveCursor(player, 0, 1)
				case Command.Right => moveCursor(player, 1, 0)
				case Command.Select => select(player)
				case Command.Cancel => cancel(player)
			}

			player.cursor.foreach(cursor => {
				playerPanels(playerNumber).terrainInfo.show(gameState.terrain(cursor.x, cursor.y))
				playerPanels(playerNumber).unitInfo.clear()
				gameState.units.find(_.position.equals(cursor)).foreach(playerPanels(playerNumber).unitInfo.show)
			})
			canvas.drawGame(gameState)
		})

	})

	private def moveCursor(player: Player, moveX: Int, moveY: Int) {
		if (player.cursor.isDefined) {
			val x = min(gameState.terrain.sizeX - 1, max(0, player.cursor.get.x + moveX))
			val y = min(gameState.terrain.sizeY - 1, max(0, player.cursor.get.y + moveY))
			player.cursor = Option(new Hex(x, y))
		} else {
			player.cursor = Option(new Hex(0, 0))
		}
	}

	private def select(player: Player) {
		uiState(player) match {
			case UiState.NoSelection =>
				if (player.cursor.isDefined) {
					player.selection = player.cursor
					uiState(player) = UiState.Selection
				}
			case UiState.Selection =>
				gameState.units.find(_.position.equals(player.selection.get)).foreach({ unit =>
					if (unit.move(gameState, player.cursor.get)) player.selection = player.cursor
				})
			case UiState.Moving =>
			case UiState.Attacking =>
		}
	}

	private def cancel(player: Player) {
		uiState(player) match {
			case UiState.NoSelection =>
			case UiState.Selection =>
				player.selection = None
				uiState(player) = UiState.NoSelection
			case UiState.Moving =>
			case UiState.Attacking =>
		}
	}

	object UiState extends Enumeration {
		type UiState = Value
		val NoSelection, Selection, Moving, Attacking = Value
	}

}