import ui.Theme

class Mod(val name: String) {

	val modPath = getClass.getResource("/mods/" + name).getPath

	val theme = new Theme(modPath + "/theme.csv")

}
