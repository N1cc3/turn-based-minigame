package hexgrid

import scalafx.geometry.Point2D

import scala.collection.mutable.ArrayBuffer
import scala.math._

class Hex(val x: Int, val y: Int) {

	val parity: Int = y & 1

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

	def drawingPosition(size: Double): Point2D = {
		new Point2D(size * sqrt(3) * x + size * sqrt(3) * parity / 2, size * 2 * y * 3 / 4)
	}

	def corner(center: Point2D, size: Double, i: Int): Point2D = {
		val angle_deg = 60 * i + 30
		val angle_rad = Pi / 180 * angle_deg
		new Point2D(center.x + size * cos(angle_rad), center.y + size * sin(angle_rad))
	}

	def corners(center: Point2D, size: Double): Array[Point2D] = {
		val corners = new ArrayBuffer[Point2D]
		for (i <- 0 until 6) {
			val corner = this.corner(center, size, i)
			corners += corner
		}
		corners.toArray
	}

	def equals(that: Hex): Boolean = this.x == that.x && this.y == that.y

}
