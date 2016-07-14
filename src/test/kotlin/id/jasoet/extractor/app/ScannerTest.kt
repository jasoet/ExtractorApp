package id.jasoet.extractor.app

import org.junit.Test

/**
 * Scanner Test
 *
 * @author Deny Prasetyo.
 */


class ScannerTest {
    @Test
    fun test() {

        loadDSL().forEach {
            val (name, dsl) = it
            println(name)
            dsl.extractRule().forEach {
                println("Rule ${it.name}")
            }
        }
    }
}