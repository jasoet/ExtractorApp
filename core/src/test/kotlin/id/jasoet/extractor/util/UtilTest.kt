package id.jasoet.extractor.util

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

class UtilTest {

    @Test
    fun digestTest() {
        val baseName = "/LaporanKepolisian:id:.docx"

        (0..3).map {
            val name = baseName.replace(":id:", it.toString())

            val resource = name.loadResourceAsStream()

            val hexDigest = resource.use {
                it.md5HexDigest()
            }

            println(hexDigest)

            Assertions.assertThat(hexDigest).isNotEmpty()
        }
    }
}