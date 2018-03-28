package ui.ingame

import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import ui.Theme

class PlayerPanel(theme: Theme) extends VBox {

	this.setPrefWidth(100)

	this.style = theme("PlayerPanel.mainBox")

	private val playerLabel = new Label("Player Name")
	playerLabel.style = theme("PlayerPanel.playerLabel")
	this.children.add(playerLabel)

}