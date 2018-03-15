package game

import hexgrid.Hex

import scala.collection.mutable.ListBuffer
import scala.math.max

class GameUnit(val unitType: UnitType, val player: Player) {

	val effects = new ListBuffer[Effect]

	// Stats
	var position: Hex = _
	var hp: Int = unitType.hp

	def range: Int = unitType.range + effects.reduceLeft(_.effectType.range + _.effectType.range)

	def speed: Int = unitType.speed + effects.reduceLeft(_.effectType.speed + _.effectType.speed)

	def cost: Int = unitType.cost

	def upkeep: Int = unitType.upkeep

	def attacks(target: GameUnit): Unit = {
		target.takesDamage(this.attack)
		this.takesDamage(target.defence)
	}

	def attack: Int = unitType.attack + effects.reduceLeft(_.effectType.attack + _.effectType.attack)

	def defence: Int = unitType.defence + effects.reduceLeft(_.effectType.defence + _.effectType.defence)

	// Events

	def takesDamage(damage: Int, skipArmor: Boolean = false): Unit = {
		if (skipArmor) {
			this.hp -= damage
		} else {
			this.hp -= max(0, damage - this.armor)
		}
		this.hp = max(0, this.hp)
	}

	// Actions

	def armor: Int = unitType.armor + effects.reduceLeft(_.effectType.armor + _.effectType.armor)

}
