package game.data

import hexgrid.Hex
import scalafx.scene.paint.Color

class Player(val color: Color) {

	var gold: Int = 0

	var selection: Option[Hex] = None
	var cursor: Hex = new Hex(0, 0)

}
