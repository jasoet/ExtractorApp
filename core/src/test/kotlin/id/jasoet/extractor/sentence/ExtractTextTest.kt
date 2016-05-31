package id.jasoet.extractor.sentence

import id.jasoet.extractor.dictionary.KeyDict
import id.jasoet.extractor.document.extractDocument
import kotlinslang.control.orElseGet
import kotlinslang.control.toOption
import kotlinslang.orElse
import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class ExtractTextTest {
    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun extractAll() {
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

        val keyDict = KeyDict("Nama")

        contentPairs.forEach {
            log.info("Data for ${it.first}")
            it.second
                    .filter { keyDict.regex.containsMatchIn(it) }
                    .forEach {
                        println(it)
                    }
        }

    }

}