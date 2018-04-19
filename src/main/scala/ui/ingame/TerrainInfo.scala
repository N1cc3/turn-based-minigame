package ui.ingame

import game.data.TerrainType
import javafx.scene.layout.{Background, BackgroundFill}
import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.layout.{CornerRadii, VBox}
import scalafx.scene.paint.Color

class TerrainInfo extends VBox {

	this.styleClass += "PlayerPanel-terrainBox"

	private val terrainNameLabel = new Label
	terrainNameLabel.styleClass += "PlayerPanel-terrainNameLabel"
	this.children.add(terrainNameLabel)

	def show(terrainType: TerrainType) {
		terrainNameLabel.text = terrainType.name
		this.background = new scalafx.scene.layout.Background(new Background(new BackgroundFill(Color.web(terrainType.color), CornerRadii.Empty, Insets.Empty)))
	}

}
