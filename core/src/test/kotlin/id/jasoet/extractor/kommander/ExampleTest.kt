package id.jasoet.extractor.kommander

import com.beust.jcommander.JCommander
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */


class ExampleTest {

    @Test
    fun run() {
        val example = KommanderExample()
        val args = arrayOf("-log", "2", "-groups", "unit", "woke", "duh", "nogood")
        val jc = JCommander(example)

        try {
            jc.parse(*args)
        } catch (e: Exception) {
            println(e.message)
            jc.usage()
        }

        println(example)
        assertThat(example.verbose).isEqualTo(2)
    }

}