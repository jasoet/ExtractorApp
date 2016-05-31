package id.jasoet.extractor.document

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

    fun content(): String {
        return this.contentHandler.toString()
    }

    fun contentLines(): List<String> {
        val separator = System.getProperty("line.separator")
        return this.content().split(separator)
    }

}