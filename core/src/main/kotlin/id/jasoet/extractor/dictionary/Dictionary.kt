package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

interface Dictionary {
    val type: DictionaryType
    val regexes: List<Regex>

    fun regexPatterns(): String {
        return this.regexes.map { it.pattern }.reduce { i, s -> "$i - $s" }
    }
}