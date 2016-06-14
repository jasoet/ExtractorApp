package id.jasoet.extractor.core.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class KeyDict(val text: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.KEY
    override val regexes: List<Regex> = listOf(Regex("($text)\\s*:", RegexOption.IGNORE_CASE))
    val description: String = "Match [$text] Key Element, Left side of colon-separated Key-Value"
}