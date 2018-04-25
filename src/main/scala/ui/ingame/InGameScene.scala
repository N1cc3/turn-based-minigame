package ui.ingame

import java.util.function.Consumer

import game.Mod
import game.data.{GameState, Player}
import hexgrid.Hex
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}
import scalafx.stage.Popup
import scalafx.util.Duration
import ui.{Command, Controls}

import scala.collection.mutable
import scala.math.{max, min}

class InGameScene(mod: Mod, gameState: GameState) extends Scene {

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

	private val playerPanels = Array(playerPanel1, playerPanel2)

	private val canvas = new GameCanvas()
	canvasBox.getChildren.add(canvas)

	canvas.drawGame(gameState)

	// Events

	private var gameEndEvent: Option[Consumer[GameState]] = None

	def onGameEnd(e: Consumer[GameState]) {
		gameEndEvent = Option(e)
	}

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
				case Command.Ready => ready(player)
			}

			playerPanels(playerNumber).terrainInfo.show(gameState.terrain(player.cursor.x, player.cursor.y))
			playerPanels(playerNumber).unitInfo.clear()
			gameState.units.find(_.position.equals(player.cursor)).foreach(playerPanels(playerNumber).unitInfo.show)
			playerPanels.indices.foreach(idx => playerPanels(idx).inTurn(gameState.playerInTurn == idx))

			canvas.drawGame(gameState)

			if (gameState.isGameOver) gameEndEvent.foreach(_.accept(gameState))
		})

	})

	private def moveCursor(player: Player, moveX: Int, moveY: Int) {
		val x = min(gameState.terrain.sizeX - 1, max(0, player.cursor.x + moveX))
		val y = min(gameState.terrain.sizeY - 1, max(0, player.cursor.y + moveY))
		player.cursor = new Hex(x, y)
	}

	private def select(player: Player) {
		uiState(player) match {
			case UiState.NoSelection =>
				gameState.units.find(_.position.equals(player.cursor)).foreach(unit => {
					if (unit.player == player) {
						player.selection = Option(player.cursor)
						uiState(player) = UiState.Selection
					}
				})
			case UiState.Selection =>
				gameState.units.find(_.position.equals(player.selection.get)).foreach(unit => {
					val moveSuccess = unit.move(gameState, player.cursor)
					if (moveSuccess) player.selection = Option(player.cursor)
					val attackSuccess = unit.attacks(gameState, player.cursor)
					if (attackSuccess) {
						player.selection = None
						uiState(player) = UiState.NoSelection
					}
				})
		}
	}

	private def cancel(player: Player) {
		uiState(player) match {
			case UiState.NoSelection =>
			case UiState.Selection =>
				player.selection = None
				uiState(player) = UiState.NoSelection
		}
	}

	private def ready(player: Player) {
		if (gameState.players(gameState.playerInTurn) != player) return
		gameState.nextTurn()
		if (gameState.playerInTurn == 0) {
			mod.saveGame(gameState)
			val popup = new Popup()
			val autosavePopupBox = new VBox
			autosavePopupBox.styleClass += "InGameScene-autosavePopupBox"
			val autosaveLabel = new Label("Game autosaved!")
			autosavePopupBox.styleClass += "InGameScene-autosaveLabel"
			autosavePopupBox.children.add(autosaveLabel)
			popup.content.add(autosavePopupBox)
			popup.show(this.getWindow)
			Timeline(List(
				KeyFrame(Duration(1500), onFinished = _ => {popup.hide()})
			)).play()
		}
	}

	object UiState extends Enumeration {
		type UiState = Value
		val NoSelection, Selection = Value
	}

}