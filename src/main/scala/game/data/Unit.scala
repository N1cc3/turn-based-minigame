package game.data

import hexgrid.Hex

import scala.collection.mutable.ListBuffer
import scala.math.max

class Unit(val unitType: UnitType, val player: Player) {

	val effects = new ListBuffer[Effect]

	var position: Hex = _
	var hp: Int = unitType.hp

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

}
