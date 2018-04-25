package game

import com.sun.javafx.css.StyleManager
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import ui.ingame.InGameScene
import ui.menus.{GameOverScreen, MainMenu, MapSelect}

class App(mod: Mod = new Mod("default")) extends PrimaryStage {

	private val mainMenu = new MainMenu()
	private val mapSelect = new MapSelect(mod)
	private val gameOverScreen = new GameOverScreen()

	StyleManager.getInstance().addUserAgentStylesheet(mod.cssFilePath)

	title = "Hexagonal Turn Based Strategy Game: " + mod.name
	scene = mainMenu

	mainMenu.onNewGame(handle {
		this.scene = mapSelect
	})

	mainMenu.onLoadGame(handle {
		val gameState = mod.loadGame(mod.getSavePath)
		gameState.isVsPlayer = mainMenu.isVsPlayer
		val inGameScene = new InGameScene(mod, gameState)
		inGameScene.onGameEnd(gameState => {
			gameOverScreen.setGameState(gameState)
			this.scene = gameOverScreen
		})
		this.scene = inGameScene
	})

	mapSelect.onStartGame(handle {
		val gameState = mod.loadScenario(mapSelect.getSelectedScenarioName)
		gameState.isVsPlayer = mainMenu.isVsPlayer
		val inGameScene = new InGameScene(mod, gameState)
		inGameScene.onGameEnd(gameState => {
			gameOverScreen.setGameState(gameState)
			this.scene = gameOverScreen
		})
		this.scene = inGameScene
	})

	gameOverScreen.onBackToMainMenu(handle {
		this.scene = mainMenu
	})

}
