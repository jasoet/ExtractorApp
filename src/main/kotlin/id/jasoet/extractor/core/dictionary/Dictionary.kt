package id.jasoet.extractor.core.dictionary

import id.jasoet.extractor.core.util.containMatchIn
import id.jasoet.extractor.core.util.find
import id.jasoet.extractor.core.util.findAll
import id.jasoet.extractor.core.util.matches
import id.jasoet.extractor.core.util.regexPatterns

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

interface Dictionary {
    val type: DictionaryType
    val regexes: List<Regex>

    fun regexPatterns(): String {
        return this.regexes.regexPatterns()
    }

    fun matches(input: String): Boolean {
        return regexes.matches(input)
    }

    fun containMatchIn(input: String): Boolean {
        return regexes.containMatchIn(input)
    }

    fun find(input: String, startIndex: Int = 0): List<MatchResult> {
        return regexes.find(input,startIndex)
    }

    fun findAll(input: String, startIndex: Int = 0): List<MatchResult> {
        return regexes.findAll(input,startIndex)
    }

}