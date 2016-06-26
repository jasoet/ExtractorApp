package id.jasoet.extractor.core.document

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.Line
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


fun List<Line>.findAnchor(anchor: Anchor): Line? {

    if (this.isEmpty()) {
        return null
    }

    return this.getOrNull(findAnchorIndex(anchor))
}

fun List<Line>.findAnchorIndex(anchor: Anchor): Int {

    if (this.isEmpty()) {
        return -1
    }

    return when (anchor) {
        is Anchor.Normal -> {
            this.indexOfFirst { line ->
                val value = line.annotations[DictionaryType.VALUE]
                val regex = Regex("\\s*(${anchor.text})\\s*")
                value != null && line.type == LineType.NORMAL && regex.matches(value)
            }
        }
        is Anchor.Predefined -> {
            this.indexOfFirst { line ->
                val regex = Regex("\\s*(${anchor.text})\\s*")
                line.type == LineType.PREDEFINED && regex.matches(line.content)
            }
        }
        is Anchor.Key -> {
            this.indexOfFirst { line ->
                val key = line.annotations[DictionaryType.KEY]
                val regex = Regex(".*(${anchor.text})\\s*:")
                key != null && line.type == LineType.KEY_VALUE && regex.matches(key)
            }
        }
        Anchor.Default -> 0
    }
}