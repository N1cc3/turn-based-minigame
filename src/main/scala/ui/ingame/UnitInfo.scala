package ui.ingame

import game.data.Unit
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.layout.{StackPane, VBox}

class UnitInfo extends VBox {

	private val nameLabel = new Label()
	nameLabel.styleClass += "UnitInfo-nameLabel"
	this.children.add(nameLabel)

	private val hpBox = new StackPane()
	hpBox.styleClass += "UnitInfo-hpBox"
	this.children.add(hpBox)

	private val hpBar = new ProgressBar()
	hpBar.styleClass += "UnitInfo-hpBar"
	hpBox.children.add(hpBar)

	private val hpLabel = new Label()
	hpLabel.styleClass += "UnitInfo-hpLabel"
	hpBox.children.add(hpLabel)

	private val statsBox = new VBox()
	statsBox.styleClass += "UnitInfo-statsBox"
	this.children.add(statsBox)

	private val attackRow = new StatRow("Attack")
	statsBox.children.add(attackRow)

	private val defenceRow = new StatRow("Defence")
	statsBox.children.add(defenceRow)

	private val armorRow = new StatRow("Armor")
	statsBox.children.add(armorRow)

	private val rangeRow = new StatRow("Range")
	statsBox.children.add(rangeRow)

	private val speedRow = new StatRow("Speed")
	statsBox.children.add(speedRow)

	clear()

	def show(unit: Unit) {
		nameLabel.text = unit.unitType.name
		attackRow.show(unit.unitType.attack)
		defenceRow.show(unit.unitType.defence)
		armorRow.show(unit.unitType.armor)
		rangeRow.show(unit.unitType.range)
		speedRow.show(unit.unitType.speed)
		hpBar.setProgress(unit.hp / unit.unitType.hp)
		hpLabel.text = unit.hp + " / " + unit.unitType.hp
	}

	def clear() {
		nameLabel.text = ""
		attackRow.show(0)
		defenceRow.show(0)
		armorRow.show(0)
		rangeRow.show(0)
		speedRow.show(0)
		hpBar.setProgress(0)
		hpLabel.text = ""
	}

}
