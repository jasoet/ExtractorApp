package id.jasoet.extractor.app

import id.jasoet.extractor.core.dictionary.keyDictionary
import org.junit.Test

/**
 * Scanner Test
 *
 * @author Deny Prasetyo.
 */


class ScannerTest {
    @Test
    fun test() {
        val content = "\tNAMA DAN ALAMAT SAKSI-SAKSI \t: \tSAMSURI, Laki-laki, Medan 24 april 1976. Karyawan Swasta. Jl. Wonosari Km 12.5 Srimulyo Piyungan, Bantul, Daerah Istimewa Yogyakarta.. (085781258892). BAHARUDIN, Laki-laki, Bantul 12 mei 1959. Petani (Ketua Rt) Timbulharjo, Sewon Bantul."
        val key = keyDictionary
            .find(content)
            .map { it.value }
            .firstOrNull()
        println(key)
        loadDSL().forEach {
            val (name, dsl) = it
            println(name)
            dsl.extractRule().forEach {
                println("Rule ${it.name}")
            }
        }
    }
}