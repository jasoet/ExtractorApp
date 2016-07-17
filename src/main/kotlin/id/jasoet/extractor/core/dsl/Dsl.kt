package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.document.line.Line
import id.jasoet.extractor.core.dsl.model.FieldResult
import id.jasoet.extractor.core.dsl.model.FieldRules

/**
 * Main DSL Class
 *
 * @author Deny Prasetyo.
 */


open class Dsl(val name: String, val language: Language.() -> Unit) {

    val className: String by lazy {
        this.javaClass.canonicalName
    }

    fun getFieldRules(): List<FieldRules> {
        val languageConstruct = Language()
        language.invoke(languageConstruct)
        return languageConstruct.fieldRules
    }

    fun extract(lines: List<Line>): List<FieldResult> {
        val fieldRules = getFieldRules()
        return fieldRules.map { fieldRule ->
            val fieldName = fieldRule.name
            val rules = fieldRule.rules

            var result = ""
            var exception: Exception? = null

            for (rule in rules) {
                try {
                    result = rule.invoke(lines) ?: ""
                    exception = null
                } catch (ex: Exception) {
                    exception = ex
                }

                if (result.isNotBlank()) {
                    break
                }
            }

            FieldResult(fieldName, result, exception)
        }
    }
}