package id.jasoet.extractor.core

import org.apache.tika.Tika
import org.slf4j.LoggerFactory


/**
 * Documentation Here
 *
 * @author Deny Prasetyo.
 */

object CoreMain {
    private val log = LoggerFactory.getLogger(CoreMain::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        /* val handler = BodyContentHandler();
         val metadata = Metadata();
         val inputStream = CoreMain.javaClass.getResourceAsStream("/Example.pdf");
         val parseContext = ParseContext();

         //parsing the document using PDF parser
         val pdfParser = PDFParser();
         pdfParser.parse(inputStream, handler, metadata, parseContext);

         val tika = Tika()
         log.info("Content Type "+tika.detect(inputStream))
         log.info("Content of the Pdf : ${handler.toString()}")
         log.info("Metadata of the PDF:");
         val metadataNames = metadata.names();

         metadataNames.forEach {
             log.info("$it : ${metadata.get(it)}")
         }*/

        val names = listOf(
            "/Example.pdf",
            "/ExcelDocument.xlsx",
            "/OldExcelDocument.xls",
            "/OldWordDocument.doc",
            "/PowerPointDocument.pptx",
            "/WordDocument.docx"
        )

        val types = names
            .map {
                check(it)
            }

        log.info(names.zip(types).joinToString(","))

    }

    fun check(name: String): String {
        val inputStream = CoreMain.javaClass.getResourceAsStream(name)
        val tika = Tika()
        return inputStream.use {
            val contentType: String = tika.detect(inputStream)
            log.info("$name Has Content-Type : $contentType")
            contentType
        }
    }
}