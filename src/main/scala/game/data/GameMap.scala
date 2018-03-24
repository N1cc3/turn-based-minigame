package game.data

class GameMap(val name: String, val sizeX: Int, val sizeY: Int) {
	val terrain = Array.ofDim[TerrainType](sizeX, sizeY)
}
