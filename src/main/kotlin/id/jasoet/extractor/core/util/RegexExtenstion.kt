package id.jasoet.extractor.core.util

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


fun List<Regex>.regexPatterns(): String {
    return this.map { it.pattern }.reduce { i, s -> "$i - $s" }
}

fun List<Regex>.matches(input: String): Boolean {
    return this.any { it.matches(input) }
}

fun List<Regex>.containMatchIn(input: String): Boolean {
    return this.any { it.containsMatchIn(input) }
}

fun List<Regex>.find(input: String, startIndex: Int = 0): List<MatchResult> {
    return this.map { it.find(input, startIndex) }.filter { it != null }.map { it!! }
}

fun List<Regex>.findAll(input: String, startIndex: Int = 0): List<MatchResult> {
    return this.map { it.findAll(input, startIndex).toList() }.flatten()
}

fun String.r(option: RegexOption = RegexOption.IGNORE_CASE): Regex {
    return Regex(this, option)
}