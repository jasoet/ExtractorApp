package id.jasoet.extractor.core

import org.apache.tika.metadata.Metadata
import org.apache.tika.sax.BodyContentHandler
import java.io.InputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class Other(override val inputStream: InputStream,
            override val metadata: Metadata,
            override val contentType: String,
            override val tikaContentType: String,
            override val contentHandler: BodyContentHandler) : Document {
}