package id.jasoet.extractor.dictionary.collection

import id.jasoet.extractor.dictionary.ClauseDict
import id.jasoet.extractor.dictionary.DateDict
import id.jasoet.extractor.dictionary.DayDict
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

val clauseDictionaries: List<ClauseDict> by lazy {
    val clauseTypeRegex = listOf(
            "KUHP",
            "KUHAP",
            "KUHAPer"
    ).reduce { f, s -> "$f|$s" }

    val juntoRegex = listOf("Yo", "Jo").reduce { f, s -> "$f|$s" }

    listOf(
            ClauseDict("\\d\\d?\\d?\\s+($clauseTypeRegex)"),
            ClauseDict("\\d\\d?\\d?(\\s+|$clauseTypeRegex)?\\s+($juntoRegex)\\s+\\d\\d?\\d?\\s+($clauseTypeRegex)")
    )

}

val dayDictionaries: List<DayDict> by lazy {
    val dayNamesRegex = listOf(
            "Sen",
            "Senin",
            "Sel",
            "Selasa",
            "Rab",
            "Rabu",
            "Kam",
            "Kamis",
            "Jum",
            "Jumat",
            "Jum'at",
            "Sab",
            "Sabtu",
            "Min",
            "Ming",
            "Minggu"
    ).reduce { f, s -> "$f|$s" }

    listOf(
            DayDict("($dayNamesRegex)")
    )
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
