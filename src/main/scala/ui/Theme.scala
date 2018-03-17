package ui

import java.io.File

import com.github.tototoshi.csv.CSVReader

import scala.collection.mutable

class Theme(modName: String) extends mutable.HashMap[String, String] {

	val reader: CSVReader = CSVReader.open(new File(getClass.getResource("/mods/" + modName + "/theme.csv").getPath))
	for (theme <- reader.allWithHeaders()) {
		this += (theme.get("ELEMENT").get -> theme.get("STYLE").get)
	}

}
