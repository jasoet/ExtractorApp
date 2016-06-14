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
 * *Functions* to extract [Document] from [File] or [InputStream]
 *
 * @author Deny Prasetyo
 */

/**
 * Extract [Document] Content from InputStream
 * Note: InputStream not closed nor reseted after this method returns
 *
 * @param parseContext Optional ParseContext to modify Parser Behaviour
 * @return [String] Content file enclosed by `Try`
 */
fun InputStream.extractDocumentContent(parseContext: ParseContext = ParseContext()): Try<String> {
    val result: Try<String> = tryOf {
        val handler = BodyContentHandler()
        val parser = AutoDetectParser()
        val metadata = Metadata()

        parser.parse(this, handler, metadata, parseContext)

        handler.toString()
    }

    return result;
}

/**
 * Extract [Document] Content from ByteArray
 *
 * @param parseContext Optional ParseContext to modify Parser Behaviour
 * @return [String] Content file enclosed by `Try`
 */
fun ByteArray.extractDocumentContent(parseContext: ParseContext = ParseContext()): Try<String> {
    val result: Try<String> = tryOf {
        val handler = BodyContentHandler()
        val parser = AutoDetectParser()
        val metadata = Metadata()
        ByteArrayInputStream(this).use {
            parser.parse(it, handler, metadata, parseContext)
            handler.toString()
        }
    }

    return result;
}


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
        val metadata = Metadata()
        val tika = Tika()

        val tikaContentType = tika.detect(this)
        parser.parse(this, handler, metadata, parseContext)
        val contentType = metadata.get("Content-Type")

        val pdfMimeType = MimeTypeResource.pdf.map { it.mimeType }
        val msOfficeMimeType = MimeTypeResource.microsoftOffice.map { it.mimeType }

        when {
            pdfMimeType.contains(contentType) ->
                Pdf(inputStream = this,
                    metadata = metadata,
                    contentHandler = handler,
                    tikaContentType = tikaContentType)

            msOfficeMimeType.contains(contentType) ->
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
 * @return [String] `Content-Type` enclosed by Try
 */
fun InputStream.extractTikaContentType(): Try<String> {
    return tryOf {
        val tika = Tika()
        tika.detect(this)
    }
}
