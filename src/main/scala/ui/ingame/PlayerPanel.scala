package ui.ingame

import scalafx.scene.control.Label
import scalafx.scene.layout._

class PlayerPanel() extends VBox {

	this.styleClass += "PlayerPanel-mainBox"

	// Player info

	private val playerLabel = new Label("Player Name")
	playerLabel.styleClass += "PlayerPanel-playerLabel"
	this.children.add(playerLabel)

	private val phaseLabel = new Label("Battle Phase")
	phaseLabel.styleClass += "PlayerPanel-phaseLabel"
	this.children.add(phaseLabel)

	// Economy

	private val ecoBox = new VBox()
	ecoBox.styleClass += "PlayerPanel-ecoBox"
	this.children.add(ecoBox)

	private val goldRow = new StatRow("Gold")
	ecoBox.children.add(goldRow)

	private val incomeRow = new StatRow("Income")
	ecoBox.children.add(incomeRow)

	private val upkeepRow = new StatRow("Upkeep")
	ecoBox.children.add(upkeepRow)

	// Terrain

	val terrainInfo = new TerrainInfo()
	terrainInfo.styleClass += "PlayerPanel-terrainInfo"
	this.children.add(terrainInfo)

	// Unit

	val unitInfo = new UnitInfo()
	unitInfo.styleClass += "PlayerPanel-unitInfo"
	this.children.add(unitInfo)

}