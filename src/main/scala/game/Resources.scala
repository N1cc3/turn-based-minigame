package game

import java.io.File

object Resources {

	def pathTo(resource: String): String = {
		val jarPath = new File(getClass.getProtectionDomain.getCodeSource.getLocation.getPath)
		jarPath.getParentFile.getAbsolutePath + "/resources/" + resource
	}

	def urlTo(resource: String): String = {
		"file:///" + pathTo(resource).replace("\\", "/")
	}

}
