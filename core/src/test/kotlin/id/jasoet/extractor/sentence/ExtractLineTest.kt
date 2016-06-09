package id.jasoet.extractor.sentence

import id.jasoet.extractor.dictionary.DictionaryContext
import id.jasoet.extractor.document.extractDocument
import id.jasoet.extractor.document.line.LineType
import id.jasoet.extractor.document.line.identifyLine
import kotlinslang.control.none
import kotlinslang.control.orElseGet
import kotlinslang.control.some
import kotlinslang.control.toOption
import kotlinslang.orElse
import org.junit.Test

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */

class ExtractLineTest {
    @Test
    fun extractAll() {

        DictionaryContext.initialize()

        val baseName = "/LaporanKepolisian:id:.docx"

        val contentPairs = (0..3).map {
            val name = baseName.replace(":id:", it.toString())

            val resourceOption = javaClass.getResourceAsStream(name).toOption()

            resourceOption
                    .map {
                        it.use {
                            name to it.extractDocument()
                                    .map {
                                        it.contentLinesOriginal().map {
                                            it.identifyLine() to it
                                        }
                                    }
                                    .onFailure { it.printStackTrace() }
                                    .orElseGet { emptyList() }
                        }
                    }
                    .orElse(name to emptyList())
        }

        val combinedContent = contentPairs.map {
            val lines = it.second

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

                            if (nextIndex < max) {
                                val nextType = lines[nextIndex].first

                                if (nextType == LineType.EMPTY) {
                                    ignoredLineNumbers.add(nextIndex)
                                    nextIndex += 1
                                }
                            }

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

            it.first to combinedList.filter { it.isDefined() }.map { it.get() }
        }

        combinedContent.forEach {
            println("====== ${it.first} ======")
            it.second.forEach {
                println("${it.first} => ${it.second}")
            }
        }
    }
}
