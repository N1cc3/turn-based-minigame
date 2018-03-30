package ui.ingame

import game.Mod
import game.data.{Player, Unit}
import hexgrid.Hex
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}
import ui.Theme

import scala.collection.mutable.ListBuffer

class InGameScene(theme: Theme, mod: Mod) extends Scene {

	private val mainBox = new HBox()
	this.content.add(mainBox)
	mainBox.style = theme("InGameScene.mainBox")

	private val playerPanel1 = new PlayerPanel(theme)
	playerPanel1.style = theme("InGameScene.playerPanel1")
	mainBox.getChildren.add(playerPanel1)

	private val canvasBox = new VBox()
	canvasBox.style = theme("InGameScene.canvasBox")
	mainBox.getChildren.add(canvasBox)

	val canvas = new GameCanvas()
	val map = mod.maps.values.toList.head
	val units = new ListBuffer[Unit]
	val unit = new Unit(mod.unitTypes("Ballista"), new Player())
	unit.position = new Hex(1, 1)
	units += unit
	canvas.drawMap(map)
	canvas.drawUnits(units.toList, map)
	canvasBox.getChildren.add(canvas)

	private val playerPanel2 = new PlayerPanel(theme)
	playerPanel2.style = theme("InGameScene.playerPanel2")
	mainBox.getChildren.add(playerPanel2)

	// Listeners

	this.widthProperty.addListener((_, _, newValue) => {
		canvas.width = newValue.doubleValue() - playerPanel1.getWidth - playerPanel2.getWidth
		canvas.drawMap(map)
		canvas.drawUnits(units.toList, map)
	})

	this.heightProperty.addListener((_, _, newValue) => {
		canvas.height = newValue.doubleValue()
		canvas.drawMap(map)
		canvas.drawUnits(units.toList, map)
	})

}