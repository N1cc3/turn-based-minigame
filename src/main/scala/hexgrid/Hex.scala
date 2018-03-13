package hexgrid

import scala.math._
import scalafx.geometry.Point2D

class Hex(val x: Int, val y: Int) {

	val even_directions = List(
		(1, 0),
		(0, -1),
		(-1, -1),
		(-1, 0),
		(-1, 1),
		(0, 1),
	)

	val odd_directions = List(
		(1, 0),
		(1, -1),
		(0, -1),
		(-1, 0),
		(0, 1),
		(1, 1),
	)

	val directions = List(even_directions, odd_directions)

	def neighbors(): List[Hex] = {
		val parity = this.y & 1
		directions(parity).map(offset => new Hex(this.x + offset._1, this.y + offset._2))
	}

	def distanceTo(other: Hex): Int = this.toCube.distanceTo(other.toCube)

	def toCube: Cube = {
		val cx = x - (y - (y & 1)) / 2
		val cz = y
		val cy = -x - cz
		new Cube(cx, cy, cz)
	}

	def neighborsWithin(range: Int): List[Hex] = {
		this.toCube.neighborsWithin(range).map(_.toHex)
	}

	def corner(center: Point2D, size: Double, i: Int): Point2D = {
		val angle_deg = 60 * i + 30
		val angle_rad = Pi / 180 * angle_deg
		new Point2D(center.x + size * cos(angle_rad), center.y + size * sin(angle_rad))
	}

}
