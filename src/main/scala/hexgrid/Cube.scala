package hexgrid

import scala.collection.mutable.ListBuffer
import scala.math._

class Cube(val x: Int, val y: Int, val z: Int) {

	def toHex: Hex = {
		val col = x + (z - (z & 1)) / 2
		val row = z
		new Hex(col, row)
	}

	def distanceTo(c: Cube): Int = abs(x - c.x) + abs(y - c.y) + abs(z - c.z) / 2

	def neighborsWithin(range: Int): List[Cube] = {
		var results = ListBuffer.empty[Cube]
		for (cx <- -range to range;
				 cy <- max(-range, -cx - range) to min(range, -cx + range)) {
			val cz = -cx - cy
			results += this + new Cube(cx, cy, cz)
		}
		results.toList
	}

	def $minus(that: Cube): Cube = new Cube(
		this.x - that.x,
		this.y - that.y,
		this.z - that.z,
	)

	def $plus(that: Cube): Cube = new Cube(
		this.x + that.x,
		this.y + that.y,
		this.z + that.z,
	)

	override def equals(that: Any): Boolean =
		that match {
			case that: Cube => that.isInstanceOf[Cube] && this.x == that.x && this.y == that.y && this.z == that.z
			case _ => false
		}

}
