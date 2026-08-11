package file

/** The storage format of the starter map included in a generated project. */
enum class MapFormat(
    val cliName: String,
    val templateBranch: String,
    val label: String,
) {
    ARCHIVE("archive", "master", "map archive (.w3x file)"),
    FOLDER("folder", "map-folder", "map folder (.w3x directory)"),
    ;

    companion object {
        fun parse(value: String): MapFormat? = values().firstOrNull {
            it.cliName == value.trim().lowercase() || it.name.lowercase() == value.trim().lowercase()
        }
    }
}
