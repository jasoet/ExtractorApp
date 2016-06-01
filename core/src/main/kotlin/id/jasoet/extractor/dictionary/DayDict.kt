package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class DayDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.DAY
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}