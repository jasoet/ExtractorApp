package id.jasoet.extractor.core.document.line

import id.jasoet.extractor.core.dictionary.DictionaryContext
import id.jasoet.extractor.core.dictionary.DictionaryType

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */

fun String.matchTitle(titleKeywords: List<String>): Boolean {
    val textFilter = titleKeywords.reduce { f, s -> "$f|$s" }
    val titleRegex = Regex("\\s*($textFilter)\\s*", RegexOption.IGNORE_CASE)
    return titleRegex.matches(this)
}

fun String.matchEmpty(): Boolean {
    val emptyRegex = Regex("\\s*", RegexOption.IGNORE_CASE)
    return emptyRegex.matches(this)
}

fun String.matchKeyValue(): Boolean {
    val keyValueRegex = Regex("[\\/\\s\\.a-zA-Z0-9\\-]+\\s*:{1}\\s+.+", RegexOption.IGNORE_CASE)
    return keyValueRegex.matches(this)
}

fun String.identifyLine(): LineType {
    val titleKeyword = DictionaryContext.titles

    val type: LineType =
        if (this.matchTitle(titleKeyword)) {
            LineType.PREDEFINED
        } else if (this.matchEmpty()) {
            LineType.EMPTY
        } else if (this.matchKeyValue()) {
            LineType.KEY_VALUE
        } else {
            LineType.NORMAL
        }

    return type
}

fun Line.getValue(default: String = ""): String {
    return this.details.getOrElse(DictionaryType.VALUE) { default }
}

fun Line.getKey(default: String = ""): String {
    return this.details.getOrElse(DictionaryType.KEY) { default }
}

