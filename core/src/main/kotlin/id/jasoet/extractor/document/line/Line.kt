package id.jasoet.extractor.document.line

import id.jasoet.extractor.dictionary.DictionaryContext

/**
 * TODO: Documentation
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
    val keyValueRegex = Regex(".+:{1}\\s+[^:]+", RegexOption.IGNORE_CASE)
    return keyValueRegex.matches(this)
}

fun String.identifyLine(): LineType {
    val titleKeyword = DictionaryContext.titles

    val type: LineType =
        if (this.matchTitle(titleKeyword)) {
            LineType.TITLE
        } else if (this.matchEmpty()) {
            LineType.EMPTY
        } else if (this.matchKeyValue()) {
            LineType.KEY_VALUE
        } else {
            LineType.NORMAL
        }

    return type
}