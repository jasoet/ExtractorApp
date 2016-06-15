package id.jasoet.extractor.app.loader

import id.jasoet.extractor.app.model.DocumentModel
import id.jasoet.extractor.core.document.extractDocument
import id.jasoet.extractor.core.util.codec.base64EncodeToString
import id.jasoet.extractor.core.util.sha1HexDigest
import org.apache.commons.io.IOUtils
import java.io.InputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

fun InputStream.loadDocumentModel(fileName: String): DocumentModel {
    return IOUtils.toByteArray(this).let {
        val id = it.sha1HexDigest()
        val originalContent = it.base64EncodeToString()

        it.extractDocument().map {
            DocumentModel(id, fileName, originalContent, it.content(), it.contentType, it.metadataMap())
        }.orElseThrow { ex ->
            IllegalStateException("Extract Document failed!!", ex)
        }
    }

}