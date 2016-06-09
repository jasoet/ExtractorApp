package id.jasoet.extractor.dictionary

import org.apache.commons.io.IOUtils

/**
 * Context that store dictionary taken from Local Resources
 * Make sure to invoke `initialize()` once per application before use.
 *
 * @author Deny Prasetyo.
 */


object DictionaryContext {
    val separator = System.getProperty("line.separator")

    lateinit var titles: List<String>
    lateinit var ages: List<String>
    lateinit var clauses: List<String>
    lateinit var crimes: List<String>
    lateinit var days: List<String>
    lateinit var genders: List<String>
    lateinit var juntos: List<String>
    lateinit var months: List<String>
    lateinit var religions: List<String>
    lateinit var timeZones: List<String>

    private fun loadLocalResource(address: String): List<String> {
        val titleStream = javaClass.getResourceAsStream(address)
        return titleStream.use {
            IOUtils.toString(it, "UTF-8").split(separator).filter { it.isNotEmpty() }
        }
    }

    fun initialize() {
        this.titles = loadLocalResource("/dictionaries/titles.dict")
        this.ages = loadLocalResource("/dictionaries/ages.dict")
        this.clauses = loadLocalResource("/dictionaries/clauses.dict")
        this.crimes = loadLocalResource("/dictionaries/crimes.dict")
        this.days = loadLocalResource("/dictionaries/days.dict")
        this.genders = loadLocalResource("/dictionaries/genders.dict")
        this.juntos = loadLocalResource("/dictionaries/juntos.dict")
        this.months = loadLocalResource("/dictionaries/months.dict")
        this.religions = loadLocalResource("/dictionaries/religions.dict")
        this.timeZones = loadLocalResource("/dictionaries/timeZones.dict")
    }
}