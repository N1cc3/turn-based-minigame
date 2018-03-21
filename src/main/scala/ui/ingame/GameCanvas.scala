package ui.ingame

import java.io.File

import scalafx.scene.canvas.Canvas
import scalafx.scene.image.Image


class GameCanvas extends Canvas {

	width = 640
	height = 480

	private val file = new File(getClass.getResource("/mods/default/terrain/grass/grass_14.png").getPath)
	private val image = new Image("file://" + file.toURI.getPath)
	this.graphicsContext2D.drawImage(image, 50, 50)

}
