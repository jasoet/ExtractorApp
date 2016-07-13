package id.jasoet.extractor.core.dsl

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


sealed class Anchor() {
    object Default : Anchor()
    class Normal(val text: String) : Anchor()
    class Predefined(val text: String) : Anchor()
    class Key(val text: String) : Anchor()
}