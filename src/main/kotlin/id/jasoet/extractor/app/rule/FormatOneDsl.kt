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


class FormatOneDsl() : Dsl({

    field("ReportNumber") {
        rule {
            startAnchor = Predefined("LAPORAN POLISI")
            endAnchor = Normal("PELAPOR")

            search { line ->
                val containsKey = line.details[DictionaryType.KEY]?.endsWith("Nomor", ignoreCase = true)
                line.type == LineType.KEY_VALUE && (containsKey ?: false)
            }

            extract(pattern = "\\w+\\/\\d+\\/\\w+\\/\\d+\\/\\w+\\/[\\w\\d\\.]+\\/[\\w\\d\\.]+")
        }
    }

    field("PoliceNum") {
        rule {
            startAnchor = Default
            endAnchor = Key("Korban")

            search(LineType.KEY_VALUE, DictionaryType.VEHICLE_NUMBER)
            extract(DictionaryType.VEHICLE_NUMBER)
        }
    }

    field("WitnessAge") {
        rule {
            extract(Key("Nama dan Alamat Saksi-Saksi"), pattern = "\\d\\d?\\s+(Thn|Tahun)")
        }
    }

    field("ReporterName") {
        rule {
            extract(Key("Nama")) { line ->
                val value = line.details.getOrElse(DictionaryType.VALUE) { "" }
                value.substring(0, value.indexOf(","))
            }
        }
    }

})