package id.jasoet.extractor.app.model

import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Field
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.annotations.Index
import org.mongodb.morphia.annotations.Indexes

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
    val id: String,
    val fileName: String,
    val originalContent: String,
    val content: String
)