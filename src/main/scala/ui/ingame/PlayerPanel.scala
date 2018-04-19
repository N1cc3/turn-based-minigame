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
	this.children.add(terrainInfo)

	// Unit

	private val unitBox = new VBox()
	unitBox.styleClass += "PlayerPanel-unitBox"
	this.children.add(unitBox)

	private val unitNameLabel = new Label("Ballista")
	unitNameLabel.styleClass += "PlayerPanel-unitNameLabel"
	unitBox.children.add(unitNameLabel)

	private val unitHpBox = new HBox()
	unitHpBox.styleClass += "PlayerPanel-unitHpBox"
	unitBox.children.add(unitHpBox)

	private val unitHpLabel = new Label("12 / 15")
	unitHpLabel.styleClass += "PlayerPanel-unitHpLabel"
	unitBox.children.add(unitHpLabel)

	private val unitStatsBox = new VBox()
	unitStatsBox.styleClass += "PlayerPanel-unitStatsBox"
	unitBox.children.add(unitStatsBox)

	private val attackRow = new StatRow("Attack")
	unitStatsBox.children.add(attackRow)

	private val defenceRow = new StatRow("Defence")
	unitStatsBox.children.add(defenceRow)

	private val armorRow = new StatRow("Armor")
	unitStatsBox.children.add(armorRow)

	private val rangeRow = new StatRow("Range")
	unitStatsBox.children.add(rangeRow)

	private val speedRow = new StatRow("Speed")
	unitStatsBox.children.add(speedRow)

}