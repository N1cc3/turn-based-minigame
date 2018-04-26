import game.data.{Terrain, TerrainType, Unit, UnitType}
import hexgrid.Hex
import org.scalatest.FunSuite

class UnitTest extends FunSuite {

	/**
		* Terrain costs:
		*   1 2 1
		* Units in order should get to:
		*  *
		*  *->
		*  *->->
		*/
	test("Unit.getMoveHexes") {
		val terrain = new Terrain("", 3, 1, "")
		terrain(0, 0) = new TerrainType("", 1, null,  null, null)
		terrain(1, 0) = new TerrainType("", 2, null,  null, null)
		terrain(2, 0) = new TerrainType("", 1, null,  null, null)
		val unit1 = new Unit(new UnitType("", 1, 1, 1, 1, 1, 1, 1, 1, null), null)
		unit1.position = new Hex(0,0)
		val unit2 = new Unit(new UnitType("", 1, 1, 1, 1, 1, 2, 1, 1, null), null)
		unit2.position = new Hex(0,0)
		val unit3 = new Unit(new UnitType("", 1, 1, 1, 1, 1, 3, 1, 1, null), null)
		unit3.position = new Hex(0,0)

		assert(unit1.getMoveHexes(terrain).contains(new Hex(1,0)) === false)

		assert(unit2.getMoveHexes(terrain).contains(new Hex(1,0)) === true)
		assert(unit2.getMoveHexes(terrain).contains(new Hex(2,0)) === false)

		assert(unit3.getMoveHexes(terrain).contains(new Hex(2,0)) === true)
	}
}