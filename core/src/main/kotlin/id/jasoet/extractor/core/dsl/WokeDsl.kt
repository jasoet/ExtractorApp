package id.jasoet.extractor.core.dsl

import id.jasoet.extractor.core.dictionary.DictionaryType
import id.jasoet.extractor.core.document.line.LineType
import id.jasoet.extractor.core.dsl.Anchor.Default
import id.jasoet.extractor.core.dsl.Anchor.Key

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


class WokeDsl() : Dsl({
    field("Something") {
        rule {
            startAnchor = Default
            endAnchor = Key("")

            search(LineType.KEY_VALUE, DictionaryType.DATE)
            extract(DictionaryType.CRIME, "Pattern", 2)
        }

        rule {
            extract(Key(""), DictionaryType.EMAIL, "Pattern", 0)
        }
    }
})