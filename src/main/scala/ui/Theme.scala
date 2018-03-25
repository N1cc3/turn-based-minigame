package ui

import com.github.tototoshi.csv.CSVReader

import scala.collection.mutable

class Theme(themeFilePath: String) extends mutable.HashMap[String, String] {

	val reader: CSVReader = CSVReader.open(themeFilePath)
	for (theme <- reader.allWithHeaders()) {
		this += (theme("ELEMENT") -> theme("STYLE"))
	}

}
