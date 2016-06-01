package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class DateDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.DATE
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}