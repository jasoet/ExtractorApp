package id.jasoet.extractor.core.dictionary

import id.jasoet.extractor.core.util.loadLocalResourceContent

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
    lateinit var keys: List<String>


    fun initialize() {
        this.titles = "/dictionaries/titles.dict".loadLocalResourceContent()
        this.ages = "/dictionaries/ages.dict".loadLocalResourceContent()
        this.clauses = "/dictionaries/clauses.dict".loadLocalResourceContent()
        this.crimes = "/dictionaries/crimes.dict".loadLocalResourceContent()
        this.days = "/dictionaries/days.dict".loadLocalResourceContent()
        this.genders = "/dictionaries/genders.dict".loadLocalResourceContent()
        this.juntos = "/dictionaries/juntos.dict".loadLocalResourceContent()
        this.months = "/dictionaries/months.dict".loadLocalResourceContent()
        this.religions = "/dictionaries/religions.dict".loadLocalResourceContent()
        this.timeZones = "/dictionaries/timeZones.dict".loadLocalResourceContent()
        this.keys = "/dictionaries/keys.dict".loadLocalResourceContent()
    }
}