package id.jasoet.extractor.dictionary

import id.jasoet.extractor.util.loadLocalResource

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


    fun initialize() {
        this.titles = "/dictionaries/titles.dict".loadLocalResource()
        this.ages = "/dictionaries/ages.dict".loadLocalResource()
        this.clauses = "/dictionaries/clauses.dict".loadLocalResource()
        this.crimes = "/dictionaries/crimes.dict".loadLocalResource()
        this.days = "/dictionaries/days.dict".loadLocalResource()
        this.genders = "/dictionaries/genders.dict".loadLocalResource()
        this.juntos = "/dictionaries/juntos.dict".loadLocalResource()
        this.months = "/dictionaries/months.dict".loadLocalResource()
        this.religions = "/dictionaries/religions.dict".loadLocalResource()
        this.timeZones = "/dictionaries/timeZones.dict".loadLocalResource()
    }
}