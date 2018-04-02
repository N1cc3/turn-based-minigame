package ui.ingame

import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}
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

}