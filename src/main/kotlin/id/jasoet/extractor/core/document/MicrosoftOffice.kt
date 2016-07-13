package id.jasoet.extractor.core.document

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class MicrosoftOffice(override val metadata: Map<String, String>,
                      override val contentType: String,
                      override val content: String) : Document {
}