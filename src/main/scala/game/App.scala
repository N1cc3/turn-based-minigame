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
		val inGameScene = new InGameScene(mod, mod.loadGame(mod.getSavePath))
		inGameScene.onGameEnd(gameState => {
			gameOverScreen.setGameState(gameState)
			this.scene = gameOverScreen
		})
		this.scene = inGameScene
	})

	mapSelect.onStartGame(handle {
		val scenario = mod.loadScenario(mapSelect.getSelectedScenarioName)
		val inGameScene = new InGameScene(mod, scenario)
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
