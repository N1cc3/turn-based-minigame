package ui.ingame

import game.data.Unit
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}

class UnitInfo extends VBox {

	private val unitNameLabel = new Label()
	unitNameLabel.styleClass += "UnitInfo-unitNameLabel"
	this.children.add(unitNameLabel)

	private val unitHpBox = new HBox()
	unitHpBox.styleClass += "UnitInfo-unitHpBox"
	this.children.add(unitHpBox)

	private val unitHpLabel = new Label("12 / 15")
	unitHpLabel.styleClass += "UnitInfo-unitHpLabel"
	this.children.add(unitHpLabel)

	private val unitStatsBox = new VBox()
	unitStatsBox.styleClass += "UnitInfo-unitStatsBox"
	this.children.add(unitStatsBox)

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

	def show(unit: Unit) {
		unitNameLabel.text = unit.unitType.name
		attackRow.show(unit.unitType.attack)
		defenceRow.show(unit.unitType.defence)
		armorRow.show(unit.unitType.armor)
		rangeRow.show(unit.unitType.range)
		speedRow.show(unit.unitType.speed)
	}

	def clear() {
		unitNameLabel.text = ""
		attackRow.show(0)
		defenceRow.show(0)
		armorRow.show(0)
		rangeRow.show(0)
		speedRow.show(0)
	}

}
