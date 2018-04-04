package game

import com.sun.javafx.css.StyleManager
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import ui.ingame.InGameScene
import ui.menus.{MainMenu, MapSelect}

class App(mod: Mod = new Mod("default")) extends PrimaryStage {

	private val mainMenu = new MainMenu()
	private val mapSelect = new MapSelect()
	private val inGameScene = new InGameScene(mod)

	StyleManager.getInstance().addUserAgentStylesheet("file://" + mod.modPath + "theme.css")

	title = "Hexagonal Turn Based Strategy Game: " + mod.name
	scene = mainMenu

	mainMenu.setNewGameEvent(handle {
		this.scene = mapSelect
	})

	mapSelect.setStartGameEvent(handle {
		this.scene = inGameScene
	})

}
