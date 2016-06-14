package id.jasoet.extractor.core.document.line

import id.jasoet.extractor.core.dictionary.DictionaryType

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