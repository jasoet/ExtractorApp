package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

interface Dictionary {
    val type: DictionaryType
    val regex: Regex

    fun regexPattern(): String {
        return this.regex.pattern
    }
}