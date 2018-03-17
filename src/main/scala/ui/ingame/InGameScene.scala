package ui.ingame

import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.{HBox, VBox}
import ui.Theme

class InGameScene(theme: Theme) extends Scene {

	private val mainBox = new HBox()
	this.content.add(mainBox)
	mainBox.style = theme.get("InGameScene.mainBox").getOrElse("")

	private val playerPanel1 = new PlayerPanel(theme)
	playerPanel1.style = theme.get("InGameScene.playerPanel1").getOrElse("")
	mainBox.getChildren.add(playerPanel1)

	private val canvas = new Canvas(640, 480)
	private val canvasBox = new VBox(canvas)
	canvasBox.style = theme.get("InGameScene.canvasBox").getOrElse("")
	mainBox.getChildren.add(canvasBox)

	private val playerPanel2 = new PlayerPanel(theme)
	playerPanel2.style = theme.get("InGameScene.playerPanel2").getOrElse("")
	mainBox.getChildren.add(playerPanel2)

}