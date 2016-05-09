package id.jasoet.extractor.core

import org.apache.tika.metadata.Metadata
import org.apache.tika.sax.BodyContentHandler
import java.io.InputStream

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


class MsWord(override val inputStream: InputStream,
             override val metadata: Metadata,
             override val contentType: String,
             override val contentHandler: BodyContentHandler) : Document {
}