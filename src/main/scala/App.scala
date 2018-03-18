import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import ui.Theme
import ui.ingame.InGameScene
import ui.menus.{MainMenu, MapSelect}

class App(mod: String = "default") extends PrimaryStage {

	private val theme = new Theme(mod)

	private val mainMenu = new MainMenu(theme)
	private val mapSelect = new MapSelect(theme)
	private val inGameScene = new InGameScene(theme)

	title = "Hexagonal Turn Based Strategy Game: " + mod
	scene = mainMenu

	mainMenu.setNewGameEvent(handle {
		this.scene = mapSelect
	})

	mapSelect.setStartGameEvent(handle {
		this.scene = inGameScene
	})

}
