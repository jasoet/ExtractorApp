package id.jasoet.extractor.dictionary

import org.apache.commons.io.IOUtils

/**
 * TODO: Documentation
 *
 * @author Deny Prasetyo.
 */


object DictionaryContext {
    lateinit var titles: List<String>

    fun initialize() {
        val titleStream = javaClass.getResourceAsStream("/dictionaries/titles.dict")
        val separator = System.getProperty("line.separator")
        this.titles = titleStream.use {
            IOUtils.toString(it, "UTF-8").split(separator).filter { it.isNotEmpty() }
        }
    }
}