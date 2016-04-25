package id.jasoet.extractor.core

import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.pdf.PDFParser
import org.apache.tika.sax.BodyContentHandler
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
        val handler = BodyContentHandler();
        val metadata = Metadata();
        val inputStream = CoreMain.javaClass.getResourceAsStream("/Example.pdf");
        val parseContext = ParseContext();

        //parsing the document using PDF parser
        val pdfParser = PDFParser();
        pdfParser.parse(inputStream, handler, metadata, parseContext);

        log.info("Content of the Pdf : ${handler.toString()}")
        log.info("Metadata of the PDF:");
        val metadataNames = metadata.names();

        metadataNames.forEach {
            log.info("$it : ${metadata.get(it)}")
        }
    }
}