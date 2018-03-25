package ui.menus

import javafx.event.{ActionEvent, EventHandler}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, RadioButton, ToggleGroup}
import scalafx.scene.layout.VBox

class ModSelect(mods: Iterable[String]) extends Scene {

	private val mainBox = new VBox(10)
	mainBox.padding = Insets(10)
	mainBox.setMinWidth(250)
	this.content.add(mainBox)

	private val toggleGroup = new ToggleGroup()

	for (modName <- mods) {
		val modBtn = new RadioButton(modName)
		mainBox.children.add(modBtn)
		modBtn.setUserData(modName)
		toggleGroup.getToggles.add(modBtn)
	}

	toggleGroup.getToggles.get(0).setSelected(true)

	private val selectModBtn = new Button("Select Mod")
	mainBox.children.add(selectModBtn)


	def getSelectedMod(): String = toggleGroup.selectedToggle.value.getUserData.toString

	// Events

	def setSelectModEvent(e: EventHandler[ActionEvent]) = {
		selectModBtn.setOnAction(e)
	}

}
