package game.data

import scalafx.scene.image.Image

class UnitType(
								val name: String,
								val hp: Int,
								val attack: Int,
								val defence: Int,
								val armor: Int,
								val range: Int,
								val speed: Int,
								val cost: Int,
								val upkeep: Int,
								val image: Image,
							)
