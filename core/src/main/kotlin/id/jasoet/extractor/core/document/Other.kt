package id.jasoet.extractor.core.document

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class Other(override val metadata: Map<String, String>,
            override val contentType: String,
            override val content: String) : Document {
}