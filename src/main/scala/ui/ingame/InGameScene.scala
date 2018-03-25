package ui.ingame

import game.Mod
import game.data.GameMap
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}
import ui.Theme

class InGameScene(theme: Theme, mod: Mod) extends Scene {

	private val mainBox = new HBox()
	this.content.add(mainBox)
	mainBox.style = theme("InGameScene.mainBox")

	private val playerPanel1 = new PlayerPanel(theme)
	playerPanel1.style = theme("InGameScene.playerPanel1")
	mainBox.getChildren.add(playerPanel1)

	private val canvas = new GameCanvas()
	private val canvasBox = new VBox(canvas)
	canvasBox.style = theme("InGameScene.canvasBox")
	mainBox.getChildren.add(canvasBox)

	private val playerPanel2 = new PlayerPanel(theme)
	playerPanel2.style = theme("InGameScene.playerPanel2")
	mainBox.getChildren.add(playerPanel2)

	val map: GameMap = mod.maps.values.toList.head
	canvas.drawMap(map)

}