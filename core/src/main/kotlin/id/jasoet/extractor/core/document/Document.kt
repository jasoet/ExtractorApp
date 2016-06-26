package id.jasoet.extractor.core.document

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.dictionary.keyDictionary
import id.jasoet.extractor.core.document.line.Line
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.document.line.identifyLine
import kotlinslang.control.none
import kotlinslang.control.some

/**
 * Interface Document
 *
 * @author Deny Prasetyo.
 */


interface Document {
    val contentType: String

    val metadata: Map<String, String>

    val content: String

    fun contentLinesOriginal(): List<String> {
        val separator = System.getProperty("line.separator")
        return this.content.split(separator)
    }

    fun contentLinesTyped(): List<Line> {
        return contentLinesOriginal().map { Line(it.identifyLine(), it) }
    }

    fun contentLinesCleaned(): List<Line> {
        val lines = contentLinesTyped()

        val ignoredLineNumbers = arrayListOf<Int>()
        val max = lines.size

        val combinedList = lines.mapIndexed { i, pair ->
            if (!ignoredLineNumbers.contains(i)) {
                val type = pair.type
                val content = pair.content

                when (type) {
                    LineType.PREDEFINED -> some(Line(type, content))
                    LineType.EMPTY, LineType.NORMAL -> {
                        var currentContent = content
                        var currentType = type

                        var nextIndex = i + 1
                        while (nextIndex < max) {
                            val nextType = lines[nextIndex].type
                            val nextContent = lines[nextIndex].content

                            if (nextType == LineType.NORMAL) {
                                currentType = LineType.NORMAL
                                currentContent += " $nextContent"
                                ignoredLineNumbers.add(nextIndex)
                                nextIndex += 1
                            } else {
                                break
                            }
                        }

                        if (currentType == LineType.NORMAL) {
                            some(Line(currentType, currentContent))
                        } else {
                            none()
                        }
                    }
                    LineType.KEY_VALUE -> {
                        var currentContent = content
                        var nextIndex = i + 1

                        while (nextIndex < max) {
                            val nextType = lines[nextIndex].type
                            val nextContent = lines[nextIndex].content

                            if (nextType == LineType.NORMAL) {
                                currentContent += " $nextContent"
                                ignoredLineNumbers.add(nextIndex)
                                nextIndex += 1
                            } else {
                                break
                            }
                        }
                        some(Line(type, currentContent))
                    }
                }
            } else {
                none()
            }
        }

        return combinedList
            .filter { it.isDefined() }
            .map {
                val combinedLine = it.get()
                combinedLine.copy(type = combinedLine.content.identifyLine())
            }
    }

    fun contentLineAnalyzed(): List<Line> {
        val cleanedLine = contentLinesCleaned()

        return cleanedLine.map { line ->
            val (type, content) = line

            when (type) {
                LineType.KEY_VALUE -> {
                    val key = keyDictionary
                        .find(content)
                        .map { it.value }
                        .firstOrNull()

                    if (key != null) {
                        val value = content.removePrefix(key)
                        val annotations = mapOf(
                            DictionaryType.KEY to key.trim(),
                            DictionaryType.VALUE to value.trim()
                        )
                        line.copy(annotations = annotations)
                    } else {
                        line
                    }
                }
                LineType.NORMAL -> {
                    line.copy(annotations = mapOf(
                        DictionaryType.VALUE to content.trim()
                    ))
                }
                else -> line
            }

        }
    }
}