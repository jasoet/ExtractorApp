package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class KeyDict(override val text: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.KEY
    override val regex: Regex = Regex("($text)\\s*:")
}