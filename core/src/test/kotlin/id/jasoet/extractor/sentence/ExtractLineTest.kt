package id.jasoet.extractor.sentence

import id.jasoet.extractor.dictionary.DictionaryContext
import id.jasoet.extractor.document.extractDocument
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

        val baseName = "/LaporanKepolisian:id:.docx"

        val contentPairs = (0..3).map {
            val name = baseName.replace(":id:", it.toString())

            val resourceOption = javaClass.getResourceAsStream(name).toOption()

            resourceOption
                .map {
                    it.use {
                        name to it.extractDocument()
                            .map { it.contentLines() }
                            .orElseGet { emptyList() }
                    }
                }
                .orElse(name to emptyList())
        }

        contentPairs.forEach {
            println("================")
            println(it.first)

            it.second.forEachIndexed { i, s ->
                println(s.identifyLine(i))
            }
        }
    }
}
