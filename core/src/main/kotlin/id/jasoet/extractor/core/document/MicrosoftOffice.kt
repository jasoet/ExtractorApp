package id.jasoet.extractor.core.document

import org.apache.tika.metadata.Metadata
import org.apache.tika.sax.BodyContentHandler
import java.io.InputStream

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


class MicrosoftOffice(override val inputStream: InputStream,
                      override val metadata: Metadata,
                      override val contentType: String,
                      override val tikaContentType: String,
                      override val contentHandler: BodyContentHandler) : Document {
}