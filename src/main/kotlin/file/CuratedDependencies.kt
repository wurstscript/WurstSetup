package file

/**
 * A community library that is commonly useful but is not part of the Wurst
 * standard library. The `generate` wizard surfaces these as opt-in extras so a
 * fresh project can pick them up front instead of running `grill install <url>`
 * afterwards.
 */
data class CuratedDependency(
    /** Short, stable handle used by the `--with-dep <id>` flag. */
    val id: String,
    /** Repository name shown to the user, e.g. "wurst-table-layout". */
    val label: String,
    /** One-line blurb shown next to the label, e.g. "AI ready UI toolkit". */
    val description: String,
    /** Dependency URL written into the wurst.build `dependencies` array. */
    val url: String
) {
    /** e.g. "wurst-table-layout (AI ready UI toolkit)" */
    val summary: String get() = "$label ($description)"
}

/**
 * The curated catalogue offered by the generate wizard. Add an entry here to
 * surface a new optional dependency; both the wizard step and the
 * `--with-dep <id>` flag pick it up automatically.
 */
object CuratedDependencies {
    val all: List<CuratedDependency> = listOf(
        CuratedDependency(
            id = "table-layout",
            label = "wurst-table-layout",
            description = "AI ready UI toolkit",
            url = "https://github.com/Frotty/wurst-table-layout"
        )
    )

    fun findById(id: String): CuratedDependency? =
        all.firstOrNull { it.id.equals(id, ignoreCase = true) }

    /** Curated entries whose URL is present in the given dependency list. */
    fun matching(dependencies: List<String>): List<CuratedDependency> =
        all.filter { dependency -> dependencies.any { it.equals(dependency.url, ignoreCase = true) } }

    val ids: List<String> get() = all.map { it.id }
}
