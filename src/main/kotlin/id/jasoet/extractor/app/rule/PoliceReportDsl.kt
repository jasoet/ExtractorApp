package id.jasoet.extractor.app.rule

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.document.line.getKey
import id.jasoet.extractor.core.document.line.getValue
import id.jasoet.extractor.core.dsl.Anchor.Key
import id.jasoet.extractor.core.dsl.Anchor.Normal
import id.jasoet.extractor.core.dsl.Anchor.Predefined
import id.jasoet.extractor.core.dsl.Dsl

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class PoliceReportDsl() : Dsl({

    field("ReportNumber") {
        rule {
            startAnchor = Predefined("LAPORAN POLISI")
            endAnchor = Normal("PELAPOR")

            search { line ->
                val containsKey = line.getKey().contains("Nomor", ignoreCase = true)
                line.type == LineType.KEY_VALUE && containsKey
            }

            extract(pattern = "\\w+\\/\\d+\\/\\w+\\/\\d+\\/\\w+\\/[\\w\\d\\.]+\\/[\\w\\d\\.]+")
        }
    }

    field("ReporterName") {
        rule {
            extract(Key("Nama")) { line ->
                val value = line.getValue()
                val index = value.indexOfAny(listOf(",", "."))
                value.substring(0, index)
            }
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

    field("Place") {
        rule {
            extract(Key("Tempat Kejadian"), DictionaryType.VALUE)
        }
    }

    field("PerpetratorName") {
        rule {
            extract(Key("Pelaku")) { line ->
                val value = line.getValue()
                if (value.contains(",")) {
                    value.substring(0, value.indexOf(","))
                } else {
                    value
                }
            }
        }
    }

    field("VictimName") {
        rule {
            extract(Key("Korban")) { line ->
                val value = line.getValue()
                val index = value.indexOfAny(listOf(","))
                if (index != -1) {
                    value.substring(0, index)
                } else {
                    value
                }
            }
        }
    }

    field("WitnessName") {
        rule {
            extract(Key("NAMA DAN ALAMAT SAKSI-SAKSI")) { line ->
                val value = line.getValue()
                val index = value.indexOfAny(listOf(",", "."))
                if (index != -1) {
                    value.substring(0, index)
                } else {
                    value
                }
            }
        }
    }

    field("CrimeType") {
        rule {
            extract(Key("TINDAK PIDANA APA"), DictionaryType.CRIME)
        }
    }

    field("CrimeClause") {
        rule {
            extract(Key("TINDAK PIDANA APA"), DictionaryType.CLAUSE)
        }
    }


})