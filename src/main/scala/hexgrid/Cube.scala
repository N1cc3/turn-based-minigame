package hexgrid

import scala.collection.mutable.ListBuffer
import scala.math._

class Cube(x: Int, y: Int, z: Int) {

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
			results += new Cube(cx, cy, cz)
		}
		results.toList
	}

}
