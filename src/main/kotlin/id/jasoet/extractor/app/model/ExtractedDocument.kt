package id.jasoet.extractor.app.model

import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.annotations.PrePersist

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Entity("extracted_document")
data class ExtractedDocument(
    @Id
    var id: String = "",
    val dslName: String = "",
    val result: Map<String, String> = emptyMap(),
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