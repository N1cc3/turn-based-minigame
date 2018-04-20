package ui

object Command extends Enumeration {
	type Command = Value
	val Up, Left, Down, Right, Select, Cancel = Value
}