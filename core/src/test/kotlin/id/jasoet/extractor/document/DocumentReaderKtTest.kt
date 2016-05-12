package id.jasoet.extractor.document

import org.apache.commons.io.FileUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File


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
    fun extractContentTypeShouldSuccess() {
        names.forEach { name ->
            val inputStream = javaClass.getResourceAsStream(name)

            inputStream.use {
                val contentType = it.extractTikaContentType()
                assertThat(contentType.isSuccess()).isTrue()
            }

        }

    }

    @Test
    fun extractDocumentFromFileShouldProduceCorrectType() {
        names.forEach { name ->
            val file = javaClass.getResourceAsStream(name).use {
                val fileTarget = File("/tmp/$name")
                FileUtils.copyInputStreamToFile(it, fileTarget)
                fileTarget
            }

            val documentTry = file.extractDocument()

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

            val tikaContentType = file.extractTikaContentType()
            tikaContentType.onFailure {
                it.printStackTrace()
            }
            assertThat(tikaContentType.isSuccess()).isTrue()

            val contentType = tikaContentType.get()

            assertThat(document.tikaContentType).isEqualToIgnoringCase(contentType)
        }
    }

    @Test
    fun extractFileContentTypeShouldSuccess() {
        names.forEach { name ->
            val file = javaClass.getResourceAsStream(name).use {
                val fileTarget = File("/tmp/$name")
                FileUtils.copyInputStreamToFile(it, fileTarget)
                fileTarget
            }


            val contentType = file.extractTikaContentType()
            assertThat(contentType.isSuccess()).isTrue()
        }

    }


}