package id.jasoet.extractor.dictionary.collection

import id.jasoet.extractor.dictionary.DateDict
import id.jasoet.extractor.dictionary.KeyDict
import id.jasoet.extractor.dictionary.TimeDict

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

fun keyDictionaries(): List<KeyDict> {
    TODO()
}

val dateDictionaries: List<DateDict> by lazy {
    val monthNames = listOf(
            "Jan",
            "Januari",
            "Feb",
            "Februari",
            "Mar",
            "Maret",
            "Apr",
            "April",
            "Mei",
            "Jun",
            "Juni",
            "Jul",
            "Juli",
            "Agu",
            "Ags",
            "Agustus",
            "Sep",
            "September",
            "Okt",
            "Oktober",
            "Nov",
            "November",
            "Nop",
            "Nopember",
            "Des",
            "Desember")

    val nameReduced = monthNames.reduce { f, s -> "$f|$s" }

    listOf(
            DateDict("\\d\\d?-\\d\\d?-\\d\\d\\d?\\d?"),
            DateDict("\\d\\d?\\s+$nameReduced\\s+\\d\\d\\d?\\d?")
    )
}

val timeDictionaries: List<TimeDict> by lazy {
    val timeZoneRegex: String = listOf("WIB", "WITA", "WIT").reduce { f, s -> "$f|$s" }

    listOf(
            TimeDict("\\d\\d?.\\d\\d?\\s+($timeZoneRegex)")
    )
}
