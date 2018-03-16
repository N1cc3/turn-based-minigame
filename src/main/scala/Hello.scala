import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import ui.menus.{Main, MapSelect}

object Hello extends JFXApp {

	private val main = new Main()

	stage = new PrimaryStage {
		title = "ScalaFX Hello World"
		scene = main
	}

	main.setNewGameEvent(handle {
		this.stage.scene = new MapSelect()
	})

}
