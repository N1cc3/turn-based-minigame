package ui

import com.github.tototoshi.csv.CSVReader
import game.Resources.pathTo

import scala.collection.mutable

class Controls extends mutable.HashMap[String, (Int, Command.Value)] {

	val controlsReader: CSVReader = CSVReader.open(pathTo("controls.csv"))
	for (controlsData <- controlsReader.allWithHeaders()) {
		val player = controlsData("PLAYER").toInt
		val command = controlsData("COMMAND") match {
			case "Up" => Command.Up
			case "Left" => Command.Left
			case "Down" => Command.Down
			case "Right" => Command.Right
			case "Select" => Command.Select
			case "Cancel" => Command.Cancel
			case "Ready" => Command.Ready
		}
		val keycode = controlsData("KEYCODE")
		this += keycode -> (player, command)
	}

}
