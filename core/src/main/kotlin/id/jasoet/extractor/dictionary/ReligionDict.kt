package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class ReligionDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.RELIGION
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}