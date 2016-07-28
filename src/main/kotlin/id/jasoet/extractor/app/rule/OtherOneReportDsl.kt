package id.jasoet.extractor.app.rule

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.document.line.getKey
import id.jasoet.extractor.core.document.line.getValue
import id.jasoet.extractor.core.dsl.Anchor.Key
import id.jasoet.extractor.core.dsl.Anchor.Predefined
import id.jasoet.extractor.core.dsl.Dsl

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class OtherOneReportDsl() : Dsl("OtherOneReport", {

    field("ReportNumber") {
        rule {
            startAnchor = Predefined("SURAT TANDA BUKTI PENERIMAAN LAPORAN")
            endAnchor = Key("Nama")

            search { line ->
                val containsKey = line.getKey().contains("No. Pol", ignoreCase = true)
                line.type == LineType.KEY_VALUE && containsKey
            }

            extract(DictionaryType.VALUE)
        }
    }

    field("ReporterName") {
        rule {
            extract(Key("Nama"), DictionaryType.VALUE)
        }
    }

    field("ReporterBirthday") {
        rule {
            extract(Key("Tempat / Tgl Lahir"), DictionaryType.DATE)
        }
    }

    field("ReporterReligion") {
        rule {
            extract(Key("Agama"), DictionaryType.RELIGION)
        }
    }

    field("ReporterAddress") {
        rule {
            extract(Key("Alamat"), DictionaryType.VALUE)
        }
    }

    field("EventDay") {
        rule {
            extract(Key("Kejadian"), DictionaryType.DAY)
        }
    }

    field("EventDate") {
        rule {
            extract(Key("Kejadian"), DictionaryType.DATE)
        }
    }


    field("Place") {
        rule {
            extract(Key("Tempat Kejadian Perkara"), DictionaryType.VALUE)
        }
    }

    field("PerpetratorName") {
        rule {
            extract(Key("Terlapor")) { line ->
                val value = line.getValue()
                if (value.contains(",")) {
                    value.substring(0, value.indexOf(","))
                } else {
                    value
                }
            }
        }
    }

    field("PerpetratorAge") {
        rule {
            extract(Key("Terlapor"), DictionaryType.AGE)
        }
    }

    field("PerpetratorReligion") {
        rule {
            extract(Key("Terlapor"), DictionaryType.RELIGION)
        }
    }

    field("PerpetratorGender") {
        rule {
            extract(Key("Terlapor"), DictionaryType.GENDER)
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


    field("CrimeType") {
        rule {
            extract(Key("Peristiwa yang dilaporkan"), DictionaryType.CRIME)
        }
    }

    field("CrimeClause") {
        rule {
            extract(Key("Peristiwa yang dilaporkan"), DictionaryType.CLAUSE)
        }
    }


})