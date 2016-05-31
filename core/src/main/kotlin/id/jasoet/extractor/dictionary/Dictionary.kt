package id.jasoet.extractor.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

interface Dictionary {
    val type: DictionaryType
    val text: String
    val regex: Regex
}