package id.jasoet.extractor.sentence

import id.jasoet.extractor.document.extractDocument
import kotlinslang.control.orElseGet
import kotlinslang.control.toOption
import kotlinslang.orElse
import org.junit.Test

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class ExtractTextTest {

    @Test
    fun extractAll() {
        val baseName = "/LaporanKepolisian:id:.docx"

        val contentPair = (0..4).map {
            val name = baseName.replace(":id:", it.toString())

            val resourceOption = javaClass.getResourceAsStream(name).toOption()

            resourceOption
                    .map {
                        it.use {
                            name to it.extractDocument()
                                    .map { it.content() }
                                    .orElseGet { it.message!! }
                        }
                    }
                    .orElse(name to "")
        }

        contentPair.forEach {
            val (name, content) = it
            println("======= $name =====")
            println(content)
        }

    }

}