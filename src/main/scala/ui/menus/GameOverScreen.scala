package ui.menus

import game.data.GameState
import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox

class GameOverScreen extends Scene {

	private val mainBox = new VBox()
	this.content = mainBox

	private val gameOverLabel = new Label("Game over!")
	mainBox.children.add(gameOverLabel)

	private val winnerLabel = new Label()
	mainBox.children.add(winnerLabel)

	private val mainMenuBtn = new Button("Back to Main Menu")
	mainBox.children.add(mainMenuBtn)

	def setGameState(gameState: GameState) {
		val playerNames = Array("Blue", "Red")
		winnerLabel.text = "Player " + playerNames(gameState.playerInTurn) + " wins the game!"
	}

	// Events

	def onBackToMainMenu(e: EventHandler[ActionEvent]) {
		mainMenuBtn.setOnAction(e)
	}

}
