package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.extract
import id.jasoet.extractor.core.document.find
import id.jasoet.extractor.core.document.findAnchor
import id.jasoet.extractor.core.document.line.Line
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.document.subList
import id.jasoet.extractor.core.dsl.exception.ExtractException
import id.jasoet.extractor.core.dsl.exception.SearchException

/**
 * RuleDetail DSL
 *
 * @author Deny Prasetyo.
 */


class RuleDetail {
    var startAnchor: Anchor? = null
    var endAnchor: Anchor? = null

    private var searchLineType: LineType = LineType.EMPTY
    private var searchDictionary: DictionaryType? = null
    private var searchPattern: String = ""
    private var searchPredicate: ((Line) -> Boolean)? = null

    private var extractDictionary: DictionaryType? = null
    private var extractPattern: String = ""
    private var extractAnchor: Anchor? = null
    private var extractIndex: Int = 0
    private var extractOperation: ((Line) -> String)? = null

    fun search(lineType: LineType = LineType.EMPTY,
               dictionary: DictionaryType? = null,
               pattern: String = "") {

        require(searchPredicate == null) {
            "Only one Search Clause Allowed"
        }

        searchLineType = lineType

        searchDictionary = dictionary

        if (pattern.isNotBlank()) {
            searchPattern = pattern
        }

    }

    fun search(predicate: (Line) -> Boolean) {
        require(searchLineType == LineType.EMPTY && searchDictionary == null && searchPattern.isBlank()) {
            "Only one Search Clause Allowed"
        }

        searchPredicate = predicate
    }

    fun extract(anchor: Anchor, dictionary: DictionaryType? = null,
                pattern: String = "", index: Int = 0) {

        require((dictionary != null || pattern.isNotBlank()) && !(dictionary != null && pattern.isNotBlank())) {
            "Only one of Dictionary or Pattern is required, cannot both"
        }

        extractAnchor = anchor
        extractDictionary = dictionary
        if (pattern.isNotBlank()) {
            extractPattern = pattern
        }
        extractIndex = index
    }

    fun extract(ops: (Line) -> String) {
        require(extractAnchor == null) {
            "Duplicate Extract Clause"
        }
        extractOperation = ops
    }

    fun extract(anchor: Anchor, ops: (Line) -> String) {
        require(extractOperation == null) {
            "Duplicate Extract Clause"
        }

        extractAnchor = anchor
        extractOperation = ops
    }

    fun extract(dictionary: DictionaryType? = null,
                pattern: String = "", index: Int = 0) {

        require((dictionary != null || pattern.isNotBlank()) && !(dictionary != null && pattern.isNotBlank())) {
            "Only one of Dictionary or Pattern is required, cannot both"
        }

        extractDictionary = dictionary
        if (pattern.isNotBlank()) {
            extractPattern = pattern
        }
        extractIndex = index
    }

    fun buildRule(): (List<Line>) -> String? {
        return if (extractAnchor != null) {
            fun(lines: List<Line>): String? {

                val subList = try {
                    if (startAnchor == null && endAnchor == null) {
                        lines
                    } else {
                        lines.subList(startAnchor, endAnchor)
                    }
                } catch (e: Exception) {
                    throw SearchException("${e.message} when calculate SubList", e)
                }

                val line = extractAnchor?.let {
                    subList.findAnchor(it)
                }

                line ?: throw SearchException("Search By Anchor [$extractAnchor] returns empty")

                return try {
                    if (extractOperation != null) {
                        extractOperation?.let {
                            it.invoke(line)
                        }
                    } else {
                        line.extract(extractDictionary, extractPattern, extractIndex)
                    }
                } catch(e: Exception) {
                    throw ExtractException("${e.message} when Extract $line", e)
                }
            }
        } else {
            fun(lines: List<Line>): String? {

                val subList = try {
                    lines.subList(startAnchor, endAnchor)
                } catch (e: Exception) {
                    throw SearchException("${e.message} when calculate SubList", e)
                }

                val line = if (searchPredicate != null) {
                    searchPredicate?.let {
                        subList.find(it)
                    }
                } else {
                    subList.find(searchLineType, searchDictionary, searchPattern)
                }

                line ?: throw SearchException("Search returns empty")

                return try {
                    if (extractOperation != null) {
                        extractOperation?.let {
                            it.invoke(line)
                        }
                    } else {
                        line.extract(extractDictionary, extractPattern, extractIndex)
                    }
                } catch(e: Exception) {
                    throw ExtractException("${e.message} when Extract $line", e)
                }
            }
        }
    }
}
