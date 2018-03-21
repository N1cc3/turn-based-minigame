import java.io.File

import scalafx.Includes.handle
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import ui.menus.ModSelect

object Launcher extends JFXApp {

	private val modSelect = new ModSelect(getMods)

	stage = new PrimaryStage {
		title = "Select Mod"
		scene = modSelect
	}

	modSelect.setSelectModEvent(handle {
		stage = new App(new Mod(modSelect.getSelectedMod()))
	})

	private def getMods(): Seq[String] = {
		val d = new File(getClass.getResource("/mods/").getPath)
		if (d.exists && d.isDirectory) {
			d.listFiles.filter(_.isDirectory).map(_.getName)
		} else {
			List[String]()
		}
	}

}
