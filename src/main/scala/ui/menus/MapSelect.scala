package ui.menus

import game.Mod
import javafx.event.{ActionEvent, EventHandler}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, RadioButton, ToggleGroup}
import scalafx.scene.layout.VBox

class MapSelect(mod: Mod) extends Scene {

	private val mainBox = new VBox()
	mainBox.styleClass += "MapSelect-mainBox"
	this.content.add(mainBox)

	private val mapButtons = mod.scenarioNames.map(new RadioButton(_))

	private val toggleGroup = new ToggleGroup()

	mapButtons.foreach(mapButton => {
		mapButton.styleClass += "MapSelect-button"
		toggleGroup.getToggles.add(mapButton)
		mainBox.children.add(mapButton)
	})
	mapButtons.head.setSelected(true)

	private val startGameBtn = new Button("Start Game")
	startGameBtn.styleClass += "MapSelect-startGameBtn"
	mainBox.children.add(startGameBtn)

	def getSelectedScenarioName: String = mapButtons.find(_.isSelected).get.getText

	// Events

	def onStartGame(e: EventHandler[ActionEvent]) {
		startGameBtn.setOnAction(e)
	}

}
