package game.data

class Map(val name: String, val sizeX: Int, val sizeY: Int, val description: String) {

	val terrain: Array[Array[TerrainType]] = Array.ofDim[TerrainType](sizeX, sizeY)

}
