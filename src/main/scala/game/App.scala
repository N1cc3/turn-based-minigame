package game

import com.sun.javafx.css.StyleManager
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import ui.ingame.InGameScene
import ui.menus.{GameOverScreen, MainMenu, MapSelect}

class App(mod: Mod = new Mod("default")) extends PrimaryStage {

	private val mainMenu = new MainMenu()
	private val mapSelect = new MapSelect(mod)
	private val inGameScene = new InGameScene(mod)
	private val gameOverScreen = new GameOverScreen()

	StyleManager.getInstance().addUserAgentStylesheet(mod.cssFilePath)

	title = "Hexagonal Turn Based Strategy Game: " + mod.name
	scene = mainMenu

	mainMenu.onNewGame(handle {
		this.scene = mapSelect
	})

	mapSelect.onStartGame(handle {
		this.scene = inGameScene
	})

	inGameScene.onGameEnd(gameState => {
		gameOverScreen.setGameState(gameState)
		this.scene = gameOverScreen
	})

	gameOverScreen.onBackToMainMenu(handle {
		this.scene = mainMenu
	})

}
