package id.jasoet.extractor.app.model

import id.jasoet.extractor.core.document.line.Line
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import org.mongodb.morphia.annotations.PrePersist

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

@Entity("processed_document")
data class ProcessedDocument(
    @Id
    var id: String = "",
    var contentLinesOriginal: List<String> = emptyList(),
    var contentLinesTyped: List<Line> = emptyList(),
    var contentLinesCleaned: List<Line> = emptyList(),
    var contentLinesAnalyzed: List<Line> = emptyList()) {

    @PrePersist
    fun validate(): Unit {
        if (id.isBlank()) {
            throw IllegalArgumentException("Id can't be Blank")
        }

        if (contentLinesOriginal.isEmpty()) {
            throw IllegalArgumentException("ContentLinesOriginal can't be Blank")
        }

        if (contentLinesTyped.isEmpty()) {
            throw IllegalArgumentException("ContentLinesTyped can't be Blank")
        }

        if (contentLinesCleaned.isEmpty()) {
            throw IllegalArgumentException("ContentLinesCleaned can't be Blank")
        }
        if (contentLinesAnalyzed.isEmpty()) {
            throw IllegalArgumentException("ContentLinesCleaned can't be Blank")
        }

    }
}