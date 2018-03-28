package game.data

import scala.collection.mutable.ListBuffer

class Game(
						val map: Map,
						val players: Seq[Player],
					) {

	val units: Iterable[Unit] = new ListBuffer[Unit]

	var playerInTurn: Int = 0

}
