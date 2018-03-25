package ui.menus

import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, RadioButton, ToggleGroup}
import scalafx.scene.layout.VBox
import ui.Theme

class MapSelect(theme: Theme) extends Scene {

	private val mainBox = new VBox()
	mainBox.style = theme("MapSelect.mainBox")
	this.content.add(mainBox)

	private val button1 = new RadioButton("Map 1")
	button1.style = theme("MapSelect.button1")
	private val button2 = new RadioButton("Map 2")
	button2.style = theme("MapSelect.button2")
	button1.setSelected(true)
	private val toggleGroup = new ToggleGroup()
	toggleGroup.getToggles.add(button1);
	toggleGroup.getToggles.add(button2);
	mainBox.children.addAll(button1, button2)

	private val startGameBtn = new Button("Start Game")
	startGameBtn.style = theme("MapSelect.startGameBtn")
	mainBox.children.add(startGameBtn)

	// Events

	def setStartGameEvent(e: EventHandler[ActionEvent]) = startGameBtn.setOnAction(e)

}
