package ui.ingame

import scalafx.geometry.HPos
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

	private val goldBox = new HBox()
	goldBox.style = theme("PlayerPanel.goldBox")
	ecoBox.children.add(goldBox)

	private val goldLabel = new Label("Gold")
	goldLabel.style = theme("PlayerPanel.goldLabel")
	goldBox.children.add(goldLabel)

	private val goldValueLabel = new Label("0")
	goldValueLabel.style = theme("PlayerPanel.goldValueLabel")
	goldBox.children.add(goldValueLabel)

	private val incomeBox = new HBox()
	incomeBox.style = theme("PlayerPanel.incomeBox")
	ecoBox.children.add(incomeBox)

	private val incomeLabel = new Label("Income")
	incomeLabel.style = theme("PlayerPanel.incomeLabel")
	incomeBox.children.add(incomeLabel)

	private val incomeValueLabel = new Label("0")
	incomeValueLabel.style = theme("PlayerPanel.incomeValueLabel")
	incomeBox.children.add(incomeValueLabel)

	private val upkeepBox = new HBox()
	upkeepBox.style = theme("PlayerPanel.upkeepBox")
	ecoBox.children.add(upkeepBox)

	private val upkeepLabel = new Label("Upkeep")
	upkeepLabel.style = theme("PlayerPanel.upkeepLabel")
	upkeepBox.children.add(upkeepLabel)

	private val upkeepValueLabel = new Label("0")
	upkeepValueLabel.style = theme("PlayerPanel.upkeepValueLabel")
	upkeepBox.children.add(upkeepValueLabel)

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

	private val attackBox = new HBox()
	attackBox.style = theme("PlayerPanel.attackBox")
	unitStatsBox.children.add(attackBox)

	private val attackLabel = new Label("Attack")
	attackLabel.style = theme("PlayerPanel.attackLabel")
	attackBox.children.add(attackLabel)

	private val attackValueLabel = new Label("0")
	attackValueLabel.style = theme("PlayerPanel.attackValueLabel")
	attackBox.children.add(attackValueLabel)

	private val defenceBox = new HBox()
	defenceBox.style = theme("PlayerPanel.defenceBox")
	unitStatsBox.children.add(defenceBox)

	private val defenceLabel = new Label("Defence")
	defenceLabel.style = theme("PlayerPanel.defenceLabel")
	defenceBox.children.add(defenceLabel)

	private val defenceValueLabel = new Label("0")
	defenceValueLabel.style = theme("PlayerPanel.defenceValueLabel")
	defenceBox.children.add(defenceValueLabel)

	private val armorBox = new HBox()
	armorBox.style = theme("PlayerPanel.armorBox")
	unitStatsBox.children.add(armorBox)

	private val armorLabel = new Label("Armor")
	armorLabel.style = theme("PlayerPanel.armorLabel")
	armorBox.children.add(armorLabel)

	private val armorValueLabel = new Label("0")
	armorValueLabel.style = theme("PlayerPanel.armorValueLabel")
	armorBox.children.add(armorValueLabel)

	private val rangeBox = new HBox()
	rangeBox.style = theme("PlayerPanel.rangeBox")
	unitStatsBox.children.add(rangeBox)

	private val rangeLabel = new Label("Range")
	rangeLabel.style = theme("PlayerPanel.rangeLabel")
	rangeBox.children.add(rangeLabel)

	private val rangeValueLabel = new Label("10")
	rangeValueLabel.style = theme("PlayerPanel.rangeValueLabel")
	rangeBox.children.add(rangeValueLabel)


	// TODO: Implement generic stat row like this:

	val gridPane = new GridPane()
	gridPane.style = theme("PlayerPanel.gridPane")
	val column1 = new ColumnConstraints()
	column1.setHgrow(Priority.Always)
	val column2 = new ColumnConstraints()
	column2.setHgrow(Priority.Always)
	column2.setHalignment(HPos.Right)
	gridPane.getColumnConstraints.addAll(column1, column2)
	unitStatsBox.children.add(gridPane)

	private val speedLabel = new Label("Speed")
	speedLabel.style = theme("PlayerPanel.speedLabel")
	gridPane.add(speedLabel, 0, 0)

	private val speedValueLabel = new Label("10000")
	speedValueLabel.style = theme("PlayerPanel.speedValueLabel")
	gridPane.add(speedValueLabel, 1, 0)

}