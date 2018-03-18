package ui.menus

import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, RadioButton, ToggleGroup}
import scalafx.scene.layout.VBox

class ModSelect(mods: Iterable[String]) extends Scene {

	private val mainBox = new VBox()
	this.content.add(mainBox)

	private val toggleGroup = new ToggleGroup()

	private var mod = "default"

	for (modName <- mods) {
		val modBtn = new RadioButton(modName)
		mainBox.children.add(modBtn)
		modBtn.setUserData(modName)
		toggleGroup.getToggles.add(modBtn)
	}

	private val selectModBtn = new Button("Select Mod")
	mainBox.children.add(selectModBtn)


	def getSelectedMod(): String = toggleGroup.selectedToggle.value.getUserData.asInstanceOf[String]

	// Events

	def setSelectModEvent(e: EventHandler[ActionEvent]) = {
		selectModBtn.setOnAction(e)
	}

}
