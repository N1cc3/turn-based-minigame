package game.data

import scala.collection.mutable.ListBuffer

class Game(
						val map: GameMap,
						val players: Seq[Player],
					) {

	val units: Iterable[GameUnit] = new ListBuffer[GameUnit]

	var playerInTurn: Int = 0

}
