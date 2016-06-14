package id.jasoet.extractor.core.dictionary

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

    fun matches(input: String): Boolean {
        return regexes.any { it.matches(input) }
    }

    fun containMatchIn(input: String): Boolean {
        return regexes.any { it.containsMatchIn(input) }
    }

}