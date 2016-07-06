package id.jasoet.extractor.core.dsl

/**
 * Main DSL Class
 *
 * @author Deny Prasetyo.
 */


open class Dsl(val language: Language.() -> Unit) {
    fun extractRule(): List<FieldRules> {
        val languageConstruct = Language()
        language.invoke(languageConstruct)
        return languageConstruct.fieldRules
    }
}