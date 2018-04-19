package game.data

import scalafx.scene.image.Image

class TerrainType(
									 val name: String,
									 val moveCost: Int,
									 val effects: List[Effect],
									 val image: Image,
									 val color: String,
								 )
