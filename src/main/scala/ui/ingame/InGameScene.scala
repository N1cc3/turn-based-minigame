package ui.ingame

import game.Mod
import game.data.GameMap
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}
import ui.Theme

class InGameScene(theme: Theme, mod: Mod) extends Scene {

	private val mainBox = new HBox()
	this.content.add(mainBox)
	mainBox.style = theme.getOrElse("InGameScene.mainBox", "")

	private val playerPanel1 = new PlayerPanel(theme)
	playerPanel1.style = theme.getOrElse("InGameScene.playerPanel1", "")
	mainBox.getChildren.add(playerPanel1)

	private val canvas = new GameCanvas()
	private val canvasBox = new VBox(canvas)
	canvasBox.style = theme.getOrElse("InGameScene.canvasBox", "")
	mainBox.getChildren.add(canvasBox)

	private val playerPanel2 = new PlayerPanel(theme)
	playerPanel2.style = theme.getOrElse("InGameScene.playerPanel2", "")
	mainBox.getChildren.add(playerPanel2)

	val map: GameMap = mod.maps.values.toList(0)
	for (y <- 0 until map.sizeY) {
		for (x <- 0 until map.sizeX) {
			canvas.getGraphicsContext2D.drawImage(map.terrain(x)(y).image, 50 * x, 50 * y, 50, 50)
		}
	}

}