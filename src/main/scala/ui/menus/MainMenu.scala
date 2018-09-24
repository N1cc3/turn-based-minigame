package ui.menus

import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, CheckBox}
import scalafx.scene.layout.VBox

class MainMenu() extends Scene {

	private val mainBox = new VBox()
	mainBox.styleClass += "MainMenu-mainBox"
	this.content.add(mainBox)

	private val vsPlayerCheckbox = new CheckBox("Versus Player")
	vsPlayerCheckbox.styleClass += "MainMenu-vsPlayerCheckbox"
	mainBox.getChildren.add(vsPlayerCheckbox)

	private val newGameBtn = new Button("New Game")
	newGameBtn.styleClass += "MainMenu-newGameBtn"
	mainBox.getChildren.add(newGameBtn)

	private val loadGameBtn = new Button("Load Game")
	loadGameBtn.styleClass += "MainMenu-loadGameBtn"
	mainBox.getChildren.add(loadGameBtn)

	def isVsPlayer: Boolean = vsPlayerCheckbox.isSelected

	// Events

	def onNewGame(e: EventHandler[ActionEvent]): Unit = newGameBtn.setOnAction(e)

	def onLoadGame(e: EventHandler[ActionEvent]): Unit = loadGameBtn.setOnAction(e)

}