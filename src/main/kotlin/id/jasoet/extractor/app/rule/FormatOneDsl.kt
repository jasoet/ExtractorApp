package id.jasoet.extractor.app.rule

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor.Default
import id.jasoet.extractor.core.dsl.Anchor.Key
import id.jasoet.extractor.core.dsl.Dsl

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


class FormatOneDsl() : Dsl({

    field("PoliceNum") {
        rule {
            startAnchor = Default
            endAnchor = Key("Korban")

            search(LineType.KEY_VALUE, pattern = "\\w\\w\\s\\d\\d\\d\\d\\s\\w\\w")
            extract(pattern = "\\w\\w\\s\\d\\d\\d\\d\\s\\w\\w")
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