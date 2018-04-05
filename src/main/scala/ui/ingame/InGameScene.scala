package ui.ingame

import game.Mod
import game.data.GameState
import hexgrid.Hex
import scalafx.scene.Scene
import scalafx.scene.input.KeyCode
import scalafx.scene.layout.{HBox, VBox}

import scala.math.{max, min}

class InGameScene(mod: Mod) extends Scene {

	private var _selectionX: Int = 0
	private var _selectionY: Int = 0

	def selectionX: Int = _selectionX

	def selectionY: Int = _selectionY

	def selectionX_=(value: Int) {
		_selectionX = min(gameState.map.sizeX - 1, max(0, value))
		drawGame()
	}

	def selectionY_=(value: Int) {
		_selectionY = min(gameState.map.sizeY - 1, max(0, value))
		drawGame()
	}

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
	canvas.drawSelection(gameState.map, new Hex(0, 0))
	canvasBox.getChildren.add(canvas)

	private val playerPanel2 = new PlayerPanel
	playerPanel2.styleClass += "InGameScene-playerPanel2"
	mainBox.getChildren.add(playerPanel2)

	def drawGame() {
		canvas.drawMap(gameState.map)
		canvas.drawUnits(gameState.units.toList, gameState.map)
		canvas.drawSelection(gameState.map, new Hex(selectionX, selectionY))
	}

	// Listeners

	this.widthProperty.addListener((_, _, newValue) => {
		canvas.width = newValue.doubleValue() - playerPanel1.getWidth - playerPanel2.getWidth
		drawGame()
	})

	this.heightProperty.addListener((_, _, newValue) => {
		canvas.height = newValue.doubleValue()
		drawGame()
	})

	this.setOnKeyPressed(ke => {
		val keyCode = ke.getCode
		keyCode match {
			// Player 0
			case KeyCode.Up.delegate => selectionY = selectionY - 1
			case KeyCode.Left.delegate => selectionX = selectionX - 1
			case KeyCode.Down.delegate => selectionY = selectionY + 1
			case KeyCode.Right.delegate => selectionX = selectionX + 1
			// Player 1
			case KeyCode.W.delegate => selectionY = selectionY - 1
			case KeyCode.A.delegate => selectionX = selectionX - 1
			case KeyCode.S.delegate => selectionY = selectionY + 1
			case KeyCode.D.delegate => selectionX = selectionX + 1
			case _ => println(keyCode.getName)
		}
	})

}