import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import ui.Theme
import ui.ingame.InGameScene
import ui.menus.{MainMenu, MapSelect}

object Hello extends JFXApp {

	private val theme = new Theme("default")

	private val mainMenu = new MainMenu(theme)
	private val mapSelect = new MapSelect(theme)
	private val inGameScene = new InGameScene(theme)

	stage = new PrimaryStage {
		title = "Hexagonal Turn Based Strategy Game"
		scene = mainMenu
	}

	mainMenu.setNewGameEvent(handle {
		this.stage.scene = mapSelect
	})

	mapSelect.setStartGameEvent(handle {
		this.stage.scene = inGameScene
	})

}
