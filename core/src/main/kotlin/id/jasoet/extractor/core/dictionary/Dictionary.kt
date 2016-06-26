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

    fun find(input: String, startIndex: Int = 0): List<MatchResult> {
        return regexes.map { it.find(input, startIndex) }.filter { it != null }.map { it!! }
    }

    fun findAll(input: String, startIndex: Int = 0): List<MatchResult> {
        return regexes.map { it.findAll(input, startIndex).toList() }.flatten()
    }

}