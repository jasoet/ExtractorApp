package id.jasoet.extractor.core.document.line

import id.jasoet.extractor.core.dictionary.DictionaryType

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

data class Line(val type: LineType,
                val content: String,
                var annotations: Map<DictionaryType, String> = emptyMap())