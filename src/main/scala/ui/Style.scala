package ui

import java.io.File

import com.github.tototoshi.csv.CSVReader

import scala.collection.mutable

class Style(modName: String) extends mutable.HashMap[String, String] {

	val reader: CSVReader = CSVReader.open(new File(getClass.getResource("/mods/" + modName + "/style.csv").getPath))
	for (style <- reader.allWithHeaders()) {
		this += (style.get("element").get -> style.get("style").get)
	}

}
