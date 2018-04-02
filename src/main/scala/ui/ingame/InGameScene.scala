package ui.ingame

import game.Mod
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

	private val canvasBox = new VBox()
	canvasBox.style = theme("InGameScene.canvasBox")
	mainBox.getChildren.add(canvasBox)

	val canvas = new GameCanvas()
	val gameState = mod.loadScenario(mod.scenarioNames.head)
	canvas.drawMap(gameState.map)
	canvas.drawUnits(gameState.units.toList, gameState.map)
	canvasBox.getChildren.add(canvas)

	private val playerPanel2 = new PlayerPanel(theme)
	playerPanel2.style = theme("InGameScene.playerPanel2")
	mainBox.getChildren.add(playerPanel2)

	// Listeners

	this.widthProperty.addListener((_, _, newValue) => {
		canvas.width = newValue.doubleValue() - playerPanel1.getWidth - playerPanel2.getWidth
		canvas.drawMap(gameState.map)
		canvas.drawUnits(gameState.units.toList, gameState.map)
	})

	this.heightProperty.addListener((_, _, newValue) => {
		canvas.height = newValue.doubleValue()
		canvas.drawMap(gameState.map)
		canvas.drawUnits(gameState.units.toList, gameState.map)
	})

}