import java.io.File

import com.github.tototoshi.csv.CSVReader
import ui.menus.{Main, MapSelect}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

object Hello extends JFXApp {

	val reader: CSVReader = CSVReader.open(new File(getClass.getResource("/with-headers.csv").getPath))
	println(reader.allWithHeaders())

	private val main = new Main()

	stage = new PrimaryStage {
		title = "ScalaFX Hello World"
		scene = main
	}

	main.newGameBtn.onAction = handle {
		this.stage.scene = new MapSelect()
	}

}
