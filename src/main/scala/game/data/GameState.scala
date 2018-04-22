package game.data

import scala.collection.mutable.ListBuffer

class GameState(
								 val terrain: Terrain,
								 val players: Array[Player],
							 ) {

	val units = new ListBuffer[Unit]

	var playerInTurn: Int = 0

	var turnCount: Int = 1

	def nextTurn() {
		turnCount += 1
		playerInTurn = (playerInTurn + 1) % players.length
		units.foreach(unit => {
			unit.movePoints = unit.unitType.speed
			unit.canAttack = true
		})
	}

	def isGameOver: Boolean = {
		var isGameOver = false
		players.foreach(player => {
			if (units.forall(_.player == player)) isGameOver = true
		})
		isGameOver
	}

}
