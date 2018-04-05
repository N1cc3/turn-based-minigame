package ui.ingame

import game.Mod
import game.data.GameState
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.scene.layout.{HBox, VBox}

class InGameScene(mod: Mod) extends Scene {

	private val mainBox = new HBox()
	this.content.add(mainBox)
	mainBox.styleClass += "InGameScene-mainBox"

	private val playerPanel1 = new PlayerPanel
	playerPanel1.styleClass += "InGameScene-playerPanel1"
	mainBox.getChildren.add(playerPanel1)

	private val canvasBox = new VBox()
	canvasBox.styleClass += "InGameScene-canvasBox"
	mainBox.getChildren.add(canvasBox)

	val canvas = new GameCanvas()
	val gameState: GameState = mod.loadScenario(mod.scenarioNames.head)
	canvas.drawMap(gameState.map)
	canvas.drawUnits(gameState.units.toList, gameState.map)
	canvasBox.getChildren.add(canvas)

	private val playerPanel2 = new PlayerPanel
	playerPanel2.styleClass += "InGameScene-playerPanel2"
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

	this.setOnKeyPressed(ke => {
		val char = ke.getCode
		char match {
			// Player 0
			case KeyCode.Up.delegate => println(char.getName)
			case KeyCode.Left.delegate => println(char.getName)
			case KeyCode.Down.delegate => println(char.getName)
			case KeyCode.Right.delegate => println(char.getName)
			// Player 1
			case KeyCode.W.delegate => println(char.getName)
			case KeyCode.A.delegate => println(char.getName)
			case KeyCode.S.delegate => println(char.getName)
			case KeyCode.D.delegate => println(char.getName)
			case _ => println(char.getName)
		}
	})

}