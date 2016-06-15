package id.jasoet.extractor.app.model

import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Field
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.annotations.Index
import org.mongodb.morphia.annotations.Indexes
import org.mongodb.morphia.annotations.PrePersist

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */
@Entity("document")
@Indexes(
    Index(value = "fileName", fields = arrayOf(Field("fileName")))
)
data class DocumentModel(
    @Id
    var id: String = "",
    var fileName: String = "",
    var originalContent: String = "",
    var content: String = "",
    var contentType: String = "",
    var metadata: Map<String, String> = emptyMap()) {

    @PrePersist
    fun validate(): Unit {
        if (id.isBlank()) {
            throw IllegalArgumentException("Id can't be Blank")
        }
        if (fileName.isBlank()) {
            throw IllegalArgumentException("FileName can't be Blank")
        }
        if (originalContent.isBlank()) {
            throw IllegalArgumentException("OriginalContent can't be Blank")
        }
        if (content.isBlank()) {
            throw IllegalArgumentException("Content can't be Blank")
        }

    }
}