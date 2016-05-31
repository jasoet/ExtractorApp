package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class ValueDict() : Dictionary {
    override lateinit var text: String
    override val type: DictionaryType = DictionaryType.VALUE
    override val regex: Regex = Regex("[\\w].+")
}