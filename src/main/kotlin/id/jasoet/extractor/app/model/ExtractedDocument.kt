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

@Entity("extracted_document")
@Indexes(
    Index(value = "dslName", fields = arrayOf(Field("dslName")))
)
data class ExtractedDocument(
    @Id
    var id: String = "",
    val dslName: String = "",
    val results: Map<String, String> = emptyMap(),
    val errors: Map<String, String> = emptyMap()) {

    @PrePersist
    fun validate(): Unit {
        if (id.isBlank()) {
            throw IllegalArgumentException("Id can't be Blank")
        }
        if (dslName.isBlank()) {
            throw IllegalArgumentException("DSL Name can't be Blank")
        }
    }
}