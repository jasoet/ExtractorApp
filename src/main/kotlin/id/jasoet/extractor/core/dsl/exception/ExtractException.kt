package id.jasoet.extractor.core.dsl.exception

/**
 * Exception for Search
 *
 * @author Deny Prasetyo.
 */


class ExtractException : Exception {
    constructor(message: String, ex: Exception?) : super(message, ex)
    constructor(message: String) : super(message)
    constructor(ex: Exception) : super(ex)
}
