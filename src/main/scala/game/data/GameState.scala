package game.data

import game.ai.Ai

import scala.collection.mutable.ListBuffer

class GameState(
								 val terrain: Terrain,
								 val players: Array[Player],
							 ) {

	var isVsPlayer = false

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
		if (!isVsPlayer && playerInTurn != 0) {
			Ai.playTurn(this, players(1)) // Hack: Not extendable to more players
			nextTurn()
		}
	}

	def isGameOver: Boolean = {
		var isGameOver = false
		players.foreach(player => {
			if (units.forall(_.player == player)) isGameOver = true
		})
		isGameOver
	}

}
