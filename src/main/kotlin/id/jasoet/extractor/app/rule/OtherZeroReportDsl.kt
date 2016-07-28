package id.jasoet.extractor.app.rule

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor.Key
import id.jasoet.extractor.core.dsl.Anchor.Normal
import id.jasoet.extractor.core.dsl.Anchor.Predefined
import id.jasoet.extractor.core.dsl.Dsl

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class OtherZeroReportDsl() : Dsl("OtherZeroReport", {

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

    field("ReporterGender") {
        rule {
            startAnchor = Normal("YANG MELAPORKAN")
            endAnchor = Key("WAKTU KEJADIAN")

            extract(Key("JENIS KELAMIN"), dictionary = DictionaryType.GENDER)
        }
    }

    field("ReporterReligion") {
        rule {
            startAnchor = Normal("YANG MELAPORKAN")
            endAnchor = Key("WAKTU KEJADIAN")

            extract(Key("AGAMA"), dictionary = DictionaryType.RELIGION)
        }
    }

    field("ReporterAddress") {
        rule {
            startAnchor = Normal("YANG MELAPORKAN")
            endAnchor = Key("WAKTU KEJADIAN")

            extract(Key("ALAMAT"), pattern = ".+")
        }
    }

    field("ReporterTelp") {
        rule {
            startAnchor = Normal("YANG MELAPORKAN")
            endAnchor = Key("WAKTU KEJADIAN")

            extract(Key("TELP/FAX/EMAIL"), pattern = ".+")
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

    field("PerpetratorName") {
        rule {
            startAnchor = Key("SIAPA TERLAPOR")
            endAnchor = Key("SAKSI-SAKSI")

            extract(Key("Nama"), pattern = ".+")
        }
    }

    field("CrimeType") {
        rule {
            extract(Key("TINDAKAN PIDANA APA"), dictionary = DictionaryType.CRIME)
        }
    }

    field("CrimeClause") {
        rule {
            extract(Key("TINDAKAN PIDANA APA"), dictionary = DictionaryType.CLAUSE)
        }
    }
})