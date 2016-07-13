package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.document.line.Line

/**
 * Rules Class
 *
 * @author Deny Prasetyo.
 */


class Rules {
    val rules: MutableList<(List<Line>) -> String?> = arrayListOf()
    fun rule(detail: RuleDetail.() -> Unit) {
        val ruleDetail = RuleDetail()
        detail.invoke(ruleDetail)
        rules.add(ruleDetail.buildRule())
    }
}