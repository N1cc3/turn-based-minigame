package ui.ingame

import scalafx.geometry.HPos
import scalafx.scene.control.Label
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority}

class StatRow(statName: String) extends GridPane {

	this.styleClass += "StatRow-mainBox"

	val column1 = new ColumnConstraints()
	column1.setHgrow(Priority.Always)
	val column2 = new ColumnConstraints()
	column2.setHgrow(Priority.Always)
	column2.setHalignment(HPos.Right)
	this.getColumnConstraints.addAll(column1, column2)

	private val statNameLabel = new Label(statName)
	statNameLabel.styleClass += "StatRow-statNameLabel"
	this.add(statNameLabel, 0, 0)

	private val statValueLabel = new Label("0")
	statValueLabel.styleClass += "StatRow-statValueLabel"
	this.add(statValueLabel, 1, 0)

	def show(value: Int) {
		statValueLabel.text = value.toString
	}

}
