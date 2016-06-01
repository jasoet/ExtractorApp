package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class GenderDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.GENDER
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}