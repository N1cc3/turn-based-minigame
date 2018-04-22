package game.data

import hexgrid.Hex

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.math.max

class Unit(val unitType: UnitType, val player: Player) {

	val effects = new ListBuffer[Effect]

	var position: Hex = _
	var hp: Int = unitType.hp
	var movePoints: Int = speed
	var canAttack: Boolean = true

	def attack: Int = unitType.attack + effects.map(_.effectType.attack).sum

	def defence: Int = unitType.defence + effects.map(_.effectType.defence).sum

	def armor: Int = unitType.armor + effects.map(_.effectType.armor).sum

	def range: Int = unitType.range + effects.map(_.effectType.range).sum

	def speed: Int = unitType.speed + effects.map(_.effectType.speed).sum

	def cost: Int = unitType.cost

	def upkeep: Int = unitType.upkeep

	// Events

	private def takesDamage(damage: Int, skipArmor: Boolean = false) {
		if (skipArmor) {
			this.hp -= damage
		} else {
			this.hp -= max(0, damage - this.armor)
		}
		this.hp = max(0, this.hp)
	}

	// Actions

	/**
		* Attempts to attack unit in target hex.
		*
		* @param gameState Current gameState
		* @param target    Position of target to attack
		* @return true if attacked successfully
		*/
	def attacks(gameState: GameState, target: Hex): Boolean = {
		val targetUnit = gameState.units.find(_.position.equals(target))
		if (targetUnit.isDefined
			&& targetUnit.get != this // TODO: Check not same player instead
			&& this.canAttack
			&& getAttackHexes(gameState.terrain).contains(target)
		) {
			this.canAttack = false
			this.movePoints = 0
			targetUnit.get.takesDamage(this.attack)
			this.takesDamage(targetUnit.get.defence)
			true
		} else false
	}

	/**
		* Attempt to move unit to target hex.
		*
		* @param gameState Current gameState
		* @param target    Destination hex
		* @return true if moved successfully
		*/
	def move(gameState: GameState, target: Hex): Boolean = {
		if (!gameState.units.map(_.position).contains(target) && getMoveHexes(gameState.terrain).contains(target)) {
			this.movePoints -= getMoveCost(target, gameState.terrain)
			this.position = target
			true
		} else false
	}

	// Helpers

	def getMoveCost(to: Hex, terrain: Terrain): Int = {
		val closed = mutable.Set[Hex]()
		val open = mutable.Set[Hex](this.position)
		val cameFrom = mutable.Map[Hex, Hex]()
		val cost = mutable.Map[Hex, Int](this.position -> 0).withDefaultValue(Int.MaxValue)

		while (open.nonEmpty) {
			val current: Hex = open.reduceLeft((h1, h2) => if (cost(h1) < cost(h2)) h1 else h2)
			if (current.equals(to)) return cost(current)

			open.remove(current)
			closed.add(current)

			val neighbors = current.neighbors()
				.filter(_.isInside(terrain.sizeX, terrain.sizeY))
				.filterNot(closed.contains)

			neighbors.foreach(neighbor => {
				open.add(neighbor)
				val newCost = cost(current) + terrain(neighbor.x, neighbor.y).moveCost
				if (newCost < cost(neighbor)) {
					cameFrom(neighbor) = current
					cost(neighbor) = newCost
				}
			})
		}

		0 // Hex not reachable
	}

	def getMoveHexes(terrain: Terrain): Set[Hex] = {
		val closed = mutable.Set[Hex]()
		val open = mutable.Set[Hex](this.position)
		val cameFrom = mutable.Map[Hex, Hex]()
		val cost = mutable.Map[Hex, Int](this.position -> 0).withDefaultValue(Int.MaxValue)

		while (open.nonEmpty) {
			val current: Hex = open.reduceLeft((h1, h2) => if (cost(h1) < cost(h2)) h1 else h2)

			open.remove(current)
			closed.add(current)

			val neighbors = current.neighbors()
				.filter(_.isInside(terrain.sizeX, terrain.sizeY))
				.filterNot(closed.contains)

			neighbors.foreach(neighbor => {
				val newCost = cost(current) + terrain(neighbor.x, neighbor.y).moveCost
				if (newCost <= this.movePoints) {
					open.add(neighbor)
					if (newCost < cost(neighbor)) {
						cameFrom(neighbor) = current
						cost(neighbor) = newCost
					}
				}
			})
		}

		closed.toSet
	}

	def getAttackHexes(terrain: Terrain): Set[Hex] = {
		this.position.neighborsWithin(this.range).filter(_.isInside(terrain.sizeX, terrain.sizeY)).toSet
	}

}
