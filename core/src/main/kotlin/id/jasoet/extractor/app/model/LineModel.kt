package id.jasoet.extractor.app.model

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

data class LineModel(var type: LineType = LineType.NORMAL,
                     var content: String = "",
                     var annotations: Map<DictionaryType, String> = emptyMap())