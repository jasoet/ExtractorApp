package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class TimeDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.TIME
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}