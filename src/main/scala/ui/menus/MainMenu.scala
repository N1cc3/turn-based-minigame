package ui.menus

import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox

class MainMenu() extends Scene {

	private val mainBox = new VBox()
	mainBox.styleClass += "MainMenu-mainBox"
	this.content.add(mainBox)

	private val newGameBtn = new Button("New Game")
	newGameBtn.styleClass += "MainMenu-newGameBtn"
	mainBox.getChildren.add(newGameBtn)

	private val loadGameBtn = new Button("Load Game")
	loadGameBtn.styleClass += "MainMenu-loadGameBtn"
	mainBox.getChildren.add(loadGameBtn)

	// Events

	def onNewGame(e: EventHandler[ActionEvent]): Unit = newGameBtn.setOnAction(e)

	def onLoadGame(e: EventHandler[ActionEvent]): Unit = loadGameBtn.setOnAction(e)

}