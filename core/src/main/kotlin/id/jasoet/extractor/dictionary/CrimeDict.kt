package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class CrimeDict(pattern: String) : Dictionary {
    override val type: DictionaryType = DictionaryType.CRIME
    override val regex: Regex = Regex(pattern, RegexOption.IGNORE_CASE)
}