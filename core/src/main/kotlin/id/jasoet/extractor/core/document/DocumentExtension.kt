package id.jasoet.extractor.core.document

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.dictionary.dictionaryMap
import id.jasoet.extractor.core.document.line.Line
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor
import id.jasoet.extractor.core.util.containMatchIn
import id.jasoet.extractor.core.util.findAll

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */

fun Line.extract(dictionary: DictionaryType? = null,
                 pattern: String = "",
                 index: Int = 0): String? {

    if (dictionary == null && pattern.isBlank()) {
        throw IllegalArgumentException("Dictionary or Pattern must Present!")
    }

    if (dictionary != null && dictionaryMap[dictionary] == null) {
        throw IllegalArgumentException("This Dictionary Type is not Mapped Yet")
    }


    val regexFromPattern = if (pattern.isNotBlank()) listOf(Regex(pattern, RegexOption.IGNORE_CASE)) else emptyList()
    val regexFromDict = dictionaryMap[dictionary].let { if (it != null) it.regexes else emptyList() }

    val regexes = regexFromPattern + regexFromDict

    return when (this.type) {
        LineType.NORMAL, LineType.KEY_VALUE -> {
            val value = this.annotations[DictionaryType.VALUE]
            if (value != null) {
                val matchResult = regexes.findAll(value).firstOrNull()
                return matchResult?.groupValues?.get(index)
            }
            null
        }
        else -> null
    }
}

fun List<Line>.find(lineType: LineType = LineType.EMPTY,
                    dictionary: DictionaryType? = null,
                    pattern: String = ""): Line? {
    if (this.isEmpty()) {
        return null
    }

    return this.getOrNull(indexOfFirst(lineType, dictionary, pattern))
}

@Throws(IllegalArgumentException::class)
fun List<Line>.indexOfFirst(lineType: LineType = LineType.EMPTY,
                            dictionary: DictionaryType? = null,
                            pattern: String = ""): Int {

    if (this.isEmpty()) {
        return -1
    }

    if (dictionary == null && pattern.isBlank()) {
        throw IllegalArgumentException("Dictionary or Pattern must Present!")
    }

    if (dictionary != null && dictionaryMap[dictionary] == null) {
        throw IllegalArgumentException("This Dictionary Type is not Mapped Yet")
    }

    val lineTypes = when (lineType) {
        LineType.KEY_VALUE -> listOf(LineType.KEY_VALUE)
        LineType.NORMAL -> listOf(LineType.NORMAL)
        else -> listOf(LineType.NORMAL, LineType.KEY_VALUE)
    }

    val regexFromPattern = if (pattern.isNotBlank()) listOf(Regex(pattern, RegexOption.IGNORE_CASE)) else emptyList()
    val regexFromDict = dictionaryMap[dictionary].let { if (it != null) it.regexes else emptyList() }

    val regexes = regexFromPattern + regexFromDict

    return this.indexOfFirst { lineTypes.contains(it.type) && regexes.containMatchIn(it.content) }
}

@Throws(IllegalStateException::class)
fun List<Line>.subList(from: Anchor? = null, to: Anchor? = null): List<Line> {
    if (this.isEmpty()) {
        return this
    }

    val fromIndex = if (from != null) this.findAnchorIndex(from) else 0
    val toIndex = if (to != null) this.findAnchorIndex(to) else this.size

    if (fromIndex == -1 || toIndex == -1) {
        throw IllegalStateException("Illegal: Anchor from produces $fromIndex, Anchor to produces $toIndex")
    }

    return this.subList(fromIndex, toIndex)
}

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
                val regex = Regex(".*(${anchor.text})\\s*:?")
                key != null && line.type == LineType.KEY_VALUE && regex.matches(key)
            }
        }
        Anchor.Default -> 0
    }
}