package ui.menus

import java.io.File

import com.github.tototoshi.csv.CSVReader
import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox

class Main extends Scene {

	val reader: CSVReader = CSVReader.open(new File(getClass.getResource("/style.csv").getPath))
	var styles = Map.empty[String, String]
	for (style <- reader.allWithHeaders()) {
		styles += (style.get("element").get -> style.get("style").get)
	}

	private val mainBox = new VBox()
	this.content.add(mainBox)

	private val newGameBtn = new Button("New Game")
	newGameBtn.style = styles.get("newGameBtn").getOrElse("")
	mainBox.getChildren.add(newGameBtn)

	private val loadGameBtn = new Button("Load Game")
	loadGameBtn.style = styles.get("loadGameBtn").getOrElse("")
	mainBox.getChildren.add(loadGameBtn)

	// Events

	def setNewGameEvent(e: EventHandler[ActionEvent]) = newGameBtn.setOnAction(e)

	def setLoadGameEvent(e: EventHandler[ActionEvent]) = loadGameBtn.setOnAction(e)

}