package id.jasoet.extractor.core.document

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * Documentation Here
 * @author Deny Prasetyo
 */

class DocumentReaderKtTest {
    private val names = listOf(
        "/other/Example.doc",
        "/other/Example.docx",
        "/other/Example.pdf",
        "/other/ExcelDocument.xlsx",
        "/other/OldExcelDocument.xls",
        "/other/OldWordDocument.doc",
        "/other/PowerPointDocument.pptx",
        "/other/WordDocument.docx"
    )

    @Test
    fun extractDocumentShouldProductCorrectType() {

        names.forEach { name ->
            val inputStream = javaClass.getResourceAsStream(name)
            inputStream.use {
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

            }

        }
    }

    @Test
    fun extractContentTypeShouldSuccess() {
        names.forEach { name ->
            val inputStream = javaClass.getResourceAsStream(name)

            inputStream.use {
                val contentType = it.extractTikaContentType()
                assertThat(contentType.isSuccess()).isTrue()
            }

        }

    }


}