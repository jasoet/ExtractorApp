package id.jasoet.extractor.dictionary.collection

import id.jasoet.extractor.dictionary.ClauseDict
import id.jasoet.extractor.dictionary.CrimeDict
import id.jasoet.extractor.dictionary.DateDict
import id.jasoet.extractor.dictionary.DayDict
import id.jasoet.extractor.dictionary.Dictionary
import id.jasoet.extractor.dictionary.DictionaryContext
import id.jasoet.extractor.dictionary.DictionaryType
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

val moneyDictionary: Dictionary by lazy {
    object : Dictionary {
        override val type: DictionaryType = DictionaryType.MONEY
        override val regexes: List<Regex> = listOf(Regex("(Rp\\.\\s+)\\d?\\d?\\d(\\.\\d\\d\\d)*(,-)?", RegexOption.IGNORE_CASE))
    }
}

val ageDictionary: Dictionary by lazy {
    val ageRegex = DictionaryContext.ages.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.AGE
        override val regexes: List<Regex> = listOf(Regex("\\d\\d?\\s+($ageRegex)", RegexOption.IGNORE_CASE))
    }
}

val religionDictionary: Dictionary by lazy {
    val religionRegex = DictionaryContext.religions.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(Regex("($religionRegex)", RegexOption.IGNORE_CASE))
    }
}

val genderDictionary: Dictionary by lazy {
    val genderTypeRegex = DictionaryContext.genders.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(Regex("($genderTypeRegex)", RegexOption.IGNORE_CASE))
    }
}

val crimeDictionaries: List<CrimeDict> by lazy {
    val crimeTypeRegex = listOf(
            "Makar",
            "Perkelahian",
            "Pemalsuan",
            "Pemerkosaan",
            "Pelecehan Seksual",
            "Pencabulan",
            "Penghinaan",
            "Pembunuhan",
            "Penganiayaan",
            "Pencurian",
            "Penggelapan",
            "Penipuan",
            "Perusakan Barang",
            "Penghancuran Barang",
            "Pemerasan",
            "Pengancaman"
    ).reduce { f, s -> "$f|$s" }

    listOf(
            CrimeDict("($crimeTypeRegex)")
    )
}

val clauseDictionaries: List<ClauseDict> by lazy {
    val clauseTypeRegex = listOf(
            "KUHP",
            "KUHAP",
            "KUHAPidana",
            "KUHAPer"
    ).reduce { f, s -> "$f|$s" }

    val juntoRegex = listOf("Yo", "Jo", "Junto", "Yunto").reduce { f, s -> "$f|$s" }

    listOf(
            ClauseDict("\\d\\d?\\d?\\s+($clauseTypeRegex)"),
            ClauseDict("\\d\\d?\\d?(\\s+|$clauseTypeRegex)?\\s+($juntoRegex)\\s+\\d\\d?\\d?\\s+($clauseTypeRegex)")
    )

}

val dayDictionaries: List<DayDict> by lazy {
    val dayNamesRegex = listOf(
            "Senin",
            "Selasa",
            "Rabu",
            "Kamis",
            "Jumat",
            "Jum'at",
            "Sabtu",
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
