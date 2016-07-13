package id.jasoet.extractor.core.dsl

/**
 * DSL Language Class
 *
 * @author Deny Prasetyo.
 */


class Language {
    val fieldRules: MutableList<FieldRules> = arrayListOf()
    fun field(name: String, rulesOps: Rules.() -> Unit) {
        val rules = Rules()
        rulesOps.invoke(rules)
        fieldRules.add(FieldRules(name, rules.rules))
    }
}