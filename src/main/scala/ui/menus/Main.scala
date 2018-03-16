package ui.menus

import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import ui.Style

class Main(style: Style) extends Scene {

	private val mainBox = new VBox()
	this.content.add(mainBox)

	private val newGameBtn = new Button("New Game")
	newGameBtn.style = style.get("newGameBtn").getOrElse("")
	mainBox.getChildren.add(newGameBtn)

	private val loadGameBtn = new Button("Load Game")
	loadGameBtn.style = style.get("loadGameBtn").getOrElse("")
	mainBox.getChildren.add(loadGameBtn)

	// Events

	def setNewGameEvent(e: EventHandler[ActionEvent]) = newGameBtn.setOnAction(e)

	def setLoadGameEvent(e: EventHandler[ActionEvent]) = loadGameBtn.setOnAction(e)

}