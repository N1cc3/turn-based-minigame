package game.data

import scala.collection.mutable.ListBuffer

class GameState(
								 val terrain: Terrain,
								 val players: Array[Player],
							 ) {

	val units = new ListBuffer[Unit]

	var playerInTurn: Int = 0

	var turnCount: Int = 1

}
