package id.jasoet.extractor.core

import org.apache.tika.metadata.Metadata
import org.apache.tika.sax.BodyContentHandler
import java.io.InputStream

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


interface Document {
    val inputStream: InputStream

    val contentType: String

    val tikaContentType: String

    val metadata: Metadata

    val contentHandler: BodyContentHandler

}