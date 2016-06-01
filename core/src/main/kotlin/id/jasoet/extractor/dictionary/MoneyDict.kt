package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class MoneyDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.CLAUSE
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}