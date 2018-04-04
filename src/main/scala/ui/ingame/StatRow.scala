package ui.ingame

import scalafx.geometry.HPos
import scalafx.scene.control.Label
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority}

class StatRow(statName: String) extends GridPane {

	this.styleClass += "statRow"

	val column1 = new ColumnConstraints()
	column1.setHgrow(Priority.Always)
	val column2 = new ColumnConstraints()
	column2.setHgrow(Priority.Always)
	column2.setHalignment(HPos.Right)
	this.getColumnConstraints.addAll(column1, column2)

	private val speedLabel = new Label(statName)
	this.add(speedLabel, 0, 0)

	private val speedValueLabel = new Label("0")
	this.add(speedValueLabel, 1, 0)

	def value_=(value: Int) = speedValueLabel.text = value.toString

}
