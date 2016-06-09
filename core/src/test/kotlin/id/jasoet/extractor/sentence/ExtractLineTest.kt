package id.jasoet.extractor.sentence

import id.jasoet.extractor.dictionary.DictionaryContext
import id.jasoet.extractor.document.extractDocument
import kotlinslang.control.toOption
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
                            name to it.extractDocument().get()
                        }
                    }
                    .get()
        }

        contentPairs.forEach {
            println("==== ${it.first} ====")

            val document = it.second

            println("============ Typed ==========")
            document.contentLinesTyped().forEach { println("${it.type} => ${it.content}") }

            println("============ Cleaned ==========")
            document.contentLinesCleaned().forEach { println("${it.type} => ${it.content}") }

        }

    }
}
