package game

class EffectType(
									val name: String,
									val duration: Int,

									// Stat bonuses
									val attack: Int,
									val defence: Int,
									val armor: Int,
									val range: Int,
									val speed: Int,

									// Applied each turn
									val damage: Int, // Negative for healing
								)
