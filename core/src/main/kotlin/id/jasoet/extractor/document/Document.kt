package id.jasoet.extractor.document

import id.jasoet.extractor.document.line.LineType
import id.jasoet.extractor.document.line.identifyLine
import kotlinslang.control.none
import kotlinslang.control.some
import org.apache.tika.metadata.Metadata
import org.apache.tika.sax.BodyContentHandler
import java.io.InputStream

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


interface Document {
    val inputStream: InputStream

    val contentType: String

    val tikaContentType: String

    val metadata: Metadata

    val contentHandler: BodyContentHandler

    fun content(): String {
        return this.contentHandler.toString()
    }

    fun contentLinesOriginal(): List<String> {
        val separator = System.getProperty("line.separator")
        return this.content().split(separator)
    }

    fun contentLinesTyped(): List<Pair<LineType, String>> {
        return contentLinesOriginal().map { it.identifyLine() to it }
    }

    fun contentLinesCleaned(): List<Pair<LineType, String>> {
        val lines = contentLinesTyped()

        val ignoredLineNumbers = arrayListOf<Int>()
        val max = lines.size

        val combinedList = lines.mapIndexed { i, pair ->
            if (!ignoredLineNumbers.contains(i)) {
                val type = pair.first
                val content = pair.second

                when (type) {
                    LineType.TITLE -> some(type to content)
                    LineType.EMPTY, LineType.NORMAL -> {
                        var currentContent = content
                        var currentType = type

                        var nextIndex = i + 1
                        while (nextIndex < max) {
                            val nextType = lines[nextIndex].first
                            val nextContent = lines[nextIndex].second

                            if (nextType == LineType.NORMAL) {
                                currentType = LineType.NORMAL
                                currentContent += nextContent
                                ignoredLineNumbers.add(nextIndex)
                                nextIndex += 1
                            } else {
                                break
                            }
                        }

                        if (currentType == LineType.NORMAL) {
                            some(currentType to currentContent)
                        } else {
                            none()
                        }
                    }
                    LineType.KEY_VALUE -> {
                        var currentContent = content
                        var nextIndex = i + 1

                        while (nextIndex < max) {
                            val nextType = lines[nextIndex].first
                            val nextContent = lines[nextIndex].second

                            if (nextType == LineType.NORMAL) {
                                currentContent += nextContent
                                ignoredLineNumbers.add(nextIndex)
                                nextIndex += 1
                            } else {
                                break
                            }
                        }
                        some(type to currentContent)
                    }
                }
            } else {
                none()
            }
        }

        return combinedList.filter { it.isDefined() }.map { it.get() }
    }

}