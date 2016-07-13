package id.jasoet.extractor.core.document

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class Pdf(override val metadata: Map<String, String>,
          override val content: String) : Document {

    override val contentType: String = "application/pdf"

}