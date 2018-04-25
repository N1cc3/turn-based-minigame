package game.ai

import game.data.{GameState, Player, Unit}
import hexgrid.Hex

import math.max
import scala.collection.immutable.ListMap
import scala.collection.mutable

object Ai {

	def playTurn(gameState: GameState, player: Player) {
		val ownUnits = gameState.units.filter(_.player == player)
		val enemyUnits = gameState.units.filterNot(_.player == player)

		ownUnits.foreach(unit => {
			val dangerLevel = mutable.Map.empty[Hex, Int].withDefaultValue(0)
			enemyUnits.foreach(enemy => {
				getAttackReach(gameState, enemy).foreach(dangerHex => {
					dangerLevel(dangerHex) += max(0, enemy.attack - unit.armor)
				})
			})
			val goodHexes = mutable.Map.empty[Hex, Int]
			goodHexes ++= unit.getMoveHexes(gameState.terrain).map(hex => hex -> 0).toMap
			goodHexes.keys.foreach(hex => {
				enemyUnits.foreach(enemy => {
					if (enemy.position.neighborsWithin(unit.range).exists(_.equals(hex))) goodHexes(hex) += unit.attack - enemy.armor
				})
				goodHexes(hex) -= dangerLevel(hex)
			})
			val bestHex = ListMap(goodHexes.toSeq.sortWith(_._2 > _._2):_*).head

			unit.move(gameState, bestHex._1) // MOVE ORDER

			val possibleTargets = enemyUnits.filter(enemy => unit.getAttackHexes(gameState.terrain).contains(enemy.position))
			if (possibleTargets.nonEmpty) {
				val bestTarget = possibleTargets.reduceLeft((enemy1, enemy2) => if (unit.attack - enemy1.armor > unit.attack - enemy2.armor) enemy1 else enemy2)
				unit.attacks(gameState, bestTarget.position) // ATTACK ORDER
			}

		})
	}

	// Helpers

	private def getAttackReach(gameState: GameState, unit: Unit): Set[Hex] = {
		val hexes = mutable.Set.empty[Hex]
		unit.getMoveHexes(gameState.terrain).foreach(hex =>
			if (hex.isInside(gameState.terrain.sizeX, gameState.terrain.sizeY)) hexes ++= hex.neighborsWithin(unit.range)
		)
		hexes.toSet
	}

}
