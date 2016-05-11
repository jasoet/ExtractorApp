package id.jasoet.extractor.core

import org.assertj.core.api.Assertions.*
import org.junit.Test


/**
 * Documentation Here

 * @author Deny Prasetyo
 */

class DocumentReaderKtTest {
    private val names = listOf(
            "/Example.doc",
            "/Example.docx",
            "/Example.pdf",
            "/ExcelDocument.xlsx",
            "/OldExcelDocument.xls",
            "/OldWordDocument.doc",
            "/PowerPointDocument.pptx",
            "/WordDocument.docx"
    )

    @Test
    fun extractDocumentShouldProductCorrectType() {

        names.forEach { name ->
            val inputStream = javaClass.getResourceAsStream(name)
            val document = inputStream.use {
                val documentTry = inputStream.extractDocument()

                assertThat(documentTry.isSuccess()).isTrue()
                val document = documentTry.get()

                when {
                    name.endsWith("doc") ||
                            name.endsWith("docx") ||
                            name.endsWith("xls") ||
                            name.endsWith("xlsx") ||
                            name.endsWith("pptx") -> assertThat(document).isInstanceOf(MicrosoftOffice::class.java)
                    name.endsWith("pdf") -> assertThat(document).isInstanceOf(Pdf::class.java)
                    else -> assertThat(document).isInstanceOf(Other::class.java)
                }

                document
            }

            javaClass.getResourceAsStream(name).use {
                val tikaContentType = it.extractTikaContentType()
                tikaContentType.onFailure {
                    it.printStackTrace()
                }
                assertThat(tikaContentType.isSuccess()).isTrue()

                val contentType = tikaContentType.get()

                assertThat(document.tikaContentType).isEqualToIgnoringCase(contentType)
            }

        }
    }

    @Test
    fun extractContentType() {
        names.forEach { name ->
            val inputStream = javaClass.getResourceAsStream(name)

            inputStream.use {
                val contentType = it.extractTikaContentType().get()
                println("$name => $contentType")
            }

        }

        println("==================")
        names.forEach { name ->
            val inputStream = javaClass.getResourceAsStream(name)

            inputStream.use {
                val document = it.extractDocument().get()
                println("$name => ${document.contentType}")
            }

        }

    }

}