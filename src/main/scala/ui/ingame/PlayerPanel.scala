package ui.ingame

import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import ui.Theme

class PlayerPanel(theme: Theme) extends VBox {

	this.style = theme.get("PlayerPanel.mainBox").getOrElse("")

	private val playerLabel = new Label("Player Name")
	playerLabel.style = theme.get("PlayerPanel.playerLabel").getOrElse("")
	this.children.add(playerLabel)

}