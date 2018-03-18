package ui.ingame

import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import ui.Theme

class PlayerPanel(theme: Theme) extends VBox {

	this.style = theme.getOrElse("PlayerPanel.mainBox", "")

	private val playerLabel = new Label("Player Name")
	playerLabel.style = theme.getOrElse("PlayerPanel.playerLabel", "")
	this.children.add(playerLabel)

}