package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class AgeDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.AGE
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}