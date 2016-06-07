package id.jasoet.extractor.document.line

import id.jasoet.extractor.dictionary.DictionaryType

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


data class LineDetail(
    val number: Int,
    val type: LineType,
    val text: String,
    val annotations: Map<DictionaryType, String>
)