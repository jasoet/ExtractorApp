package id.jasoet.extractor.app.rule

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor.Default
import id.jasoet.extractor.core.dsl.Anchor.Key
import id.jasoet.extractor.core.dsl.Anchor.Normal
import id.jasoet.extractor.core.dsl.Anchor.Predefined
import id.jasoet.extractor.core.dsl.Dsl

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class OtherZeroDsl() : Dsl("OtherZero", {

    field("ReportNumber") {
        rule {
            startAnchor = Predefined("LAPORAN POLISI")
            endAnchor = Normal("YANG MELAPORKAN")

            search { line ->
                val containsKey = line.details[DictionaryType.KEY]?.contains("Nomor", ignoreCase = true)
                line.type == LineType.KEY_VALUE && (containsKey ?: false)
            }

            extract(pattern = ".+")
        }
    }

    field("ReporterName") {
        rule {
            startAnchor = Normal("YANG MELAPORKAN")
            endAnchor = Key("JENIS KELAMIN")

            extract(Key("Nama"), pattern = ".+")
        }
    }

    field("EventDay") {
        rule {
            extract(Key("WAKTU KEJADIAN"), DictionaryType.DAY)
        }
    }

    field("EventDate") {
        rule {
            extract(Key("WAKTU KEJADIAN"), DictionaryType.DATE)
        }
    }

    field("EventTime") {
        rule {
            extract(Key("WAKTU KEJADIAN"), DictionaryType.TIME)
        }
    }

    field("SuspectName") {
        rule {
            startAnchor = Normal("SIAPA TERLAPOR")
            endAnchor = Default

            extract(Key("Nama"), pattern = ".+")
        }
    }

})