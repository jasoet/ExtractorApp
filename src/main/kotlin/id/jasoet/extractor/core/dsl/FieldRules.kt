package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.document.line.Line

/**
 * Data Class to Store Field with Its Rules
 *
 * @author Deny Prasetyo.
 */


data class FieldRules(val name: String, val rules: List<(List<Line>) -> String?>)