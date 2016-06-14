package id.jasoet.extractor.app.loader

import id.jasoet.extractor.app.model.DocumentModel
import id.jasoet.extractor.core.document.extractDocumentContent
import id.jasoet.extractor.core.util.sha1HexDigest
import kotlinslang.orElse
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils
import java.io.InputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
private val base64Codec = Base64()

fun InputStream.loadDocumentModel(fileName: String): DocumentModel {
    val byteArray = IOUtils.toByteArray(this)
    val id = byteArray.sha1HexDigest()
    val originalContent = base64Codec.encodeToString(byteArray)
    val content = byteArray.extractDocumentContent().orElse("ERROR when Read Content!!")
    return DocumentModel(id, fileName, originalContent, content)
}