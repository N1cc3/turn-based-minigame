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

	def attack: Int = unitType.attack + effects.map(_.effectType.attack).sum

	def defence: Int = unitType.defence + effects.map(_.effectType.defence).sum

	def armor: Int = unitType.armor + effects.map(_.effectType.armor).sum

	def range: Int = unitType.range + effects.map(_.effectType.range).sum

	def speed: Int = unitType.speed + effects.map(_.effectType.speed).sum

	def cost: Int = unitType.cost

	def upkeep: Int = unitType.upkeep

	// Events

	def takesDamage(damage: Int, skipArmor: Boolean = false) {
		if (skipArmor) {
			this.hp -= damage
		} else {
			this.hp -= max(0, damage - this.armor)
		}
		this.hp = max(0, this.hp)
	}

	// Actions

	def attacks(target: Unit) {
		target.takesDamage(this.attack)
		this.takesDamage(target.defence)
	}

	def move(hex: Hex) {

	}

	// Helpers

	def getMoveHexes(terrain: Terrain): Set[Hex] = {
		val hexes = new mutable.HashSet[Hex]

		val neighbors = new ListBuffer[(Hex, Hex, Int)]
		this.position.neighbors().foreach(hex => {
			if (hex.isInside(terrain.sizeX, terrain.sizeY)) {
				neighbors += Tuple3(this.position, hex, this.movePoints)
			}
		})

		var frontier = neighbors.toList
		while (frontier.nonEmpty) {
			val newFrontier = expandFrontier(frontier)
			hexes ++= newFrontier.map(_._1)
			frontier = newFrontier
		}

		def expandFrontier(frontier: List[(Hex, Hex, Int)]): List[(Hex, Hex, Int)] = {
			val newFrontier = new ListBuffer[(Hex, Hex, Int)]
			frontier.foreach(hexTuple => {
				val from = hexTuple._1
				val to = hexTuple._2
				val movePoints = hexTuple._3
				if (to.isInside(terrain.sizeX, terrain.sizeY)) {
					val cost = terrain(to.x, to.y).moveCost
					if (movePoints >= cost) {
						to.neighbors().foreach(hex => {
							if (hex.isInside(terrain.sizeX, terrain.sizeY)) {
								newFrontier += Tuple3(to, hex, movePoints - cost)
							}
						})
					}
				}
			})
			newFrontier.toList
		}

		Set.empty ++ hexes
	}

	def attackHexes(): List[Hex] = {
		this.position.neighborsWithin(this.range)
	}

}
