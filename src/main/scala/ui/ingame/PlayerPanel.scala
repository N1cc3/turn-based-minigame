package ui.ingame

import scalafx.scene.control.Label
import scalafx.scene.layout._
import ui.Theme

class PlayerPanel(theme: Theme) extends VBox {

	this.style = theme("PlayerPanel.mainBox")

	// Player info

	private val playerLabel = new Label("Player Name")
	playerLabel.style = theme("PlayerPanel.playerLabel")
	this.children.add(playerLabel)

	private val phaseLabel = new Label("Battle Phase")
	phaseLabel.style = theme("PlayerPanel.phaseLabel")
	this.children.add(phaseLabel)

	// Economy

	private val ecoBox = new VBox()
	ecoBox.style = theme("PlayerPanel.ecoBox")
	this.children.add(ecoBox)

	private val goldRow = new StatRow("Gold")
	ecoBox.children.add(goldRow)

	private val incomeRow = new StatRow("Income")
	ecoBox.children.add(incomeRow)

	private val upkeepRow = new StatRow("Upkeep")
	ecoBox.children.add(upkeepRow)

	// Terrain

	private val terrainBox = new VBox()
	terrainBox.style = theme("PlayerPanel.terrainBox")
	this.children.add(terrainBox)

	private val terrainNameLabel = new Label("Grass")
	terrainNameLabel.style = theme("PlayerPanel.terrainNameLabel")
	terrainBox.children.add(terrainNameLabel)

	// Unit

	private val unitBox = new VBox()
	unitBox.style = theme("PlayerPanel.unitBox")
	this.children.add(unitBox)

	private val unitNameLabel = new Label("Ballista")
	unitNameLabel.style = theme("PlayerPanel.unitNameLabel")
	unitBox.children.add(unitNameLabel)

	private val unitHpBox = new HBox()
	unitHpBox.style = theme("PlayerPanel.unitHpBox")
	unitBox.children.add(unitHpBox)

	private val unitHpLabel = new Label("12 / 15")
	unitHpLabel.style = theme("PlayerPanel.unitHpLabel")
	unitBox.children.add(unitHpLabel)

	private val unitStatsBox = new VBox()
	unitStatsBox.style = theme("PlayerPanel.unitStatsBox")
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