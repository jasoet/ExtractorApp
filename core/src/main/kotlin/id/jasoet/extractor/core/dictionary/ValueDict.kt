package id.jasoet.extractor.core.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class ValueDict() : Dictionary {
    override val type: DictionaryType = DictionaryType.VALUE
    override val regexes: List<Regex> = listOf(Regex("[\\w].+"))
    val description: String = "Match Value from Right Side of colon-separated key-value"
}