package ui.menus

import scalafx.scene.Scene
import scalafx.scene.control.{RadioButton, ToggleGroup}
import scalafx.scene.layout.VBox

class MapSelect extends Scene {

	private val mainBox = new VBox(10)
	this.content.add(mainBox)

	private val button1 = new RadioButton("Map 1")
	private val button2 = new RadioButton("Map 2")
	button1.setSelected(true)
	private val toggleGroup = new ToggleGroup()
	toggleGroup.getToggles.add(button1);
	toggleGroup.getToggles.add(button2);
	mainBox.children.addAll(button1, button2)

}
