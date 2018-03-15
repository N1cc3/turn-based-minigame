package ui.menus

import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox

//object Main {
//	val NEW_GAME = new EventType[Event]("NEW_GAME")
//}

class Main extends Scene {

	val mainBox = new VBox(10)
	this.content.add(mainBox)

	val newGameBtn = new Button("New Game")
	newGameBtn.setOnAction((e) => println("New Game pressed!"))
	this.getChildren.add(newGameBtn)

	//	Event.fireEvent(this, new Event(Main.NEW_GAME))

}