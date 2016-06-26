package id.jasoet.extractor.core.util

import id.jasoet.extractor.core.dictionary.keyDictionary
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class UtilTest {

    @Test
    fun keyDictTest() {
        val text = "\t \t                    B. KORBAN \t: \tPT. ADIRA FINANCE, Alamat Jl. Ipda tut harsono 55A Yogyakarta."
        val match = keyDictionary.find(text)
        match.map {
            println(text.removePrefix(it.value))
            println(it.range.last)
            println(it.value)
            it.value
        }.firstOrNull()
    }

    @Test
    fun digestTest() {
        val baseName = "/LaporanKepolisian:id:.docx"

        (0..3).map {
            val name = baseName.replace(":id:", it.toString())

            val resource = name.loadResourceAsStream()

            val hexDigest = resource.use {
                it.md5HexDigest()
            }

            println("md5 => $hexDigest")

            Assertions.assertThat(hexDigest).isNotEmpty()

            val sha1Resource = name.loadResourceAsStream()

            val sha1Digest = sha1Resource.use {
                it.sha1HexDigest()
            }

            println("sha1 => $sha1Digest")

            Assertions.assertThat(sha1Digest).isNotEmpty()
        }
    }
}