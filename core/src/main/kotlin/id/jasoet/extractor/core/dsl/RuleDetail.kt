package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.Line
import id.jasoet.extractor.core.document.line.LineType

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


class RuleDetail {
    var startAnchor: Anchor? = null
    var endAnchor: Anchor? = null

    fun search(lineType: LineType = LineType.EMPTY,
               dictionary: DictionaryType? = null,
               pattern: String = "") {

    }

    fun search(predicate: (Line) -> Boolean) {

    }

    fun extract(ops: (String) -> String) {

    }

    fun extract(dictionary: DictionaryType? = null,
                pattern: String = "", index: Int = 0) {

    }

    fun extract(anchor: Anchor, dictionary: DictionaryType? = null,
                pattern: String = "", index: Int = 0) {

    }
}