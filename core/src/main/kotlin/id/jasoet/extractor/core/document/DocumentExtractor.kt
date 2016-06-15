package id.jasoet.extractor.core.document

import id.jasoet.extractor.core.util.MimeTypeResource
import kotlinslang.control.Try
import kotlinslang.control.tryOf
import org.apache.tika.Tika
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.sax.BodyContentHandler
import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * *Functions* to extract [Document] from [ByteArray] or [InputStream]
 *
 * @author Deny Prasetyo
 */

/**
 * Extract [Document] from InputStream
 * Note: InputStream not closed nor reseted after this method returns
 *
 * @param parseContext Optional ParseContext to modify Parser Behaviour
 * @return [Document] file enclosed by `Try`
 */
fun InputStream.extractDocument(parseContext: ParseContext = ParseContext()): Try<Document> {
    val result: Try<Document> = tryOf {
        val handler = BodyContentHandler()
        val parser = AutoDetectParser()
        val tikaMetadata = Metadata()

        parser.parse(this, handler, tikaMetadata, parseContext)
        val contentType = tikaMetadata.get("Content-Type")

        val pdfMimeType = MimeTypeResource.pdf.map { it.mimeType }
        val msOfficeMimeType = MimeTypeResource.microsoftOffice.map { it.mimeType }

        val metadata = tikaMetadata.names().map {
            it to tikaMetadata.get(it)
        }.toMap()

        when {
            pdfMimeType.contains(contentType) ->
                Pdf(metadata = metadata,
                    content = handler.toString())

            msOfficeMimeType.contains(contentType) ->
                MicrosoftOffice(metadata = metadata,
                    contentType = contentType,
                    content = handler.toString())

            else ->
                Other(metadata = metadata,
                    contentType = contentType,
                    content = handler.toString())
        }

    }

    return result;
}


/**
 * Extract [Document] from ByteArray
 *
 * @param parseContext Optional ParseContext to modify Parser Behaviour
 * @return [Document] file enclosed by `Try`
 */
fun ByteArray.extractDocument(parseContext: ParseContext = ParseContext()): Try<Document> {
    val result: Try<Document> =
        ByteArrayInputStream(this).use {
            it.extractDocument(parseContext)
        }

    return result;
}

/**
 * Extract Tika Content Type. Can be different from Real ContentType.
 * Only small portion of InputStream will be used, so InputStream not touched
 * Note: InputStream not closed after this method returns
 * @return [String] `Content-Type` enclosed by Try
 */
fun InputStream.extractTikaContentType(): Try<String> {
    return tryOf {
        val tika = Tika()
        tika.detect(this)
    }
}
