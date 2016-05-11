package id.jasoet.extractor.core

import kotlinslang.control.Try
import kotlinslang.control.tryOf
import org.apache.tika.Tika
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.sax.BodyContentHandler
import java.io.InputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */


/**
 * Extract `Document` from InputStream
 * Note: InputStream not closed nor reseted after this method returns
 *
 * @param parseContext Optional ParseContext to modify Parser Behaviour
 * @return `Document` file enclosed by `Try`
 */
fun InputStream.extractDocument(parseContext: ParseContext = ParseContext()): Try<Document> {
    val result: Try<Document> = tryOf {
        val handler = BodyContentHandler()
        val parser = AutoDetectParser()
        val metadata = Metadata()
        val tika = Tika()

        val tikaContentType = tika.detect(this)
        parser.parse(this, handler, metadata, parseContext)
        val contentType = metadata.get("Content-Type")
        when (tikaContentType) {
            "application/pdf" ->
                Pdf(inputStream = this,
                        metadata = metadata,
                        contentHandler = handler,
                        tikaContentType = tikaContentType)

            "application/x-tika-ooxml",
            "application/x-tika-msoffice" ->
                MicrosoftOffice(inputStream = this,
                        tikaContentType = tikaContentType,
                        metadata = metadata,
                        contentType = contentType,
                        contentHandler = handler)

            else ->
                Other(inputStream = this,
                        metadata = metadata,
                        contentType = contentType,
                        contentHandler = handler,
                        tikaContentType = tikaContentType)
        }

    }

    return result;
}

/**
 * Extract Tika Content Type. Can be different from Real ContentType.
 * Only small portion of InputStream will be used, so InputStream not touched
 * Note: InputStream not closed after this method returns
 */
fun InputStream.extractTikaContentType(): Try<String> {
    return tryOf {
        val tika = Tika()
        tika.detect(this)
    }
}