package game.data

import scala.collection.mutable.ListBuffer

class GameState(
								 val map: Map,
								 val players: Seq[Player],
							 ) {

	val units = new ListBuffer[Unit]

	var playerInTurn: Int = 0

	var turnCount: Int = 1

}
