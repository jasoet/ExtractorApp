package id.jasoet.extractor.core.dsl

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


sealed class Anchor() {
    object Default : Anchor() {
        override fun toString(): String {
            return "Anchor Default"
        }
    }

    class Normal(val text: String) : Anchor() {
        override fun toString(): String {
            return "Anchor Normal($text)"
        }
    }

    class Predefined(val text: String) : Anchor() {
        override fun toString(): String {
            return "Anchor Predefined($text)"
        }
    }

    class Key(val text: String) : Anchor() {
        override fun toString(): String {
            return "Anchor Key($text)"
        }
    }
}