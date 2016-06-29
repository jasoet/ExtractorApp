package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor.Default
import id.jasoet.extractor.core.dsl.Anchor.Key

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


class ExampleDsl() : Dsl({

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

})