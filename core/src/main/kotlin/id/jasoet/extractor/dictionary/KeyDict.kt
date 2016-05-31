package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class KeyDict(val text: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.KEY
    override val regex: Regex = Regex("($text)\\s*:", RegexOption.IGNORE_CASE)
    val description: String = "Match [$text] Key Element, Left side of colon-separated Key-Value"
}