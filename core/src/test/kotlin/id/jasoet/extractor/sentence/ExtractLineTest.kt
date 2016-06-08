package id.jasoet.extractor.sentence

import id.jasoet.extractor.dictionary.DictionaryContext
import id.jasoet.extractor.document.extractDocument
import id.jasoet.extractor.document.line.LineType
import id.jasoet.extractor.document.line.identifyLine
import kotlinslang.control.orElseGet
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

        val baseName = "/LaporanKepolisian:id:.pdf"

        val contentPairs = (0..3).map {
            val name = baseName.replace(":id:", it.toString())

            val resourceOption = javaClass.getResourceAsStream(name).toOption()

            resourceOption
                .map {
                    it.use {
                        name to it.extractDocument()
                            .map {
                                it.contentLines().map {
                                    it.identifyLine() to it
                                }
                            }
                            .onFailure { it.printStackTrace() }
                            .orElseGet { emptyList() }
                    }
                }
                .orElse(name to emptyList())
        }

        contentPairs.map {
            println("================")
            println(it.first)

            val combinedLines = arrayListOf<Pair<LineType, String>>()

            val lines = it.second

            val ignoredLineNumbers = arrayListOf<Int>()

            (0..lines.size).forEach { i ->
                if (!ignoredLineNumbers.contains(i)) {
                    val max = lines.size
                    val type = lines[i].first
                    val content = lines[i].second

                    when (type) {
                        LineType.TITLE -> combinedLines.add(type to content)
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
                                combinedLines.add(currentType to currentContent)
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
                            combinedLines.add(type to currentContent)
                        }

                    }
                }


            }

            it.second.forEachIndexed { i, s ->
                println("${s.first} => ${s.second}")
            }


            it.first to combinedLines
        }
    }
}
