package game.data

class Terrain(val name: String, val sizeX: Int, val sizeY: Int, val description: String) {

	private val terrain: Array[Array[TerrainType]] = Array.ofDim[TerrainType](sizeX, sizeY)

	def apply(x: Int, y: Int): TerrainType = {
		terrain(x)(y)
	}

	def update(x: Int, y: Int, terrainType: TerrainType) {
		terrain(x)(y) = terrainType
	}

}
