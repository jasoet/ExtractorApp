package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class OtherDict(pattern: String, override val type: DictionaryType) : Dictionary {
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}