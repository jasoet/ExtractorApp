package id.jasoet.extractor.dictionary.collection

import id.jasoet.extractor.dictionary.AgeDict
import id.jasoet.extractor.dictionary.ClauseDict
import id.jasoet.extractor.dictionary.CrimeDict
import id.jasoet.extractor.dictionary.DateDict
import id.jasoet.extractor.dictionary.DayDict
import id.jasoet.extractor.dictionary.GenderDict
import id.jasoet.extractor.dictionary.KeyDict
import id.jasoet.extractor.dictionary.MoneyDict
import id.jasoet.extractor.dictionary.ReligionDict
import id.jasoet.extractor.dictionary.TimeDict

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

fun keyDictionaries(): List<KeyDict> {
    TODO()
}

val moneyDictionaries: List<MoneyDict> by lazy {

    listOf(
            MoneyDict("(Rp\\.\\s+)\\d?\\d?\\d(\\.\\d\\d\\d)*(,-)?")
    )
}

val ageDictionaries: List<AgeDict> by lazy {
    val ageRegex = listOf(
            "Th",
            "Tahun"
    ).reduce { f, s -> "$f|$s" }

    listOf(
            AgeDict("\\d\\d?\\s+($ageRegex)")
    )
}

val religionDictionaries: List<ReligionDict> by lazy {
    val religionRegex = listOf(
            "Muslim",
            "Islam",
            "Protestan",
            "Kristen",
            "Nasrani",
            "Budha",
            "Katolik",
            "Hindu"
    ).reduce { f, s -> "$f|$s" }

    listOf(
            ReligionDict("($religionRegex)")
    )
}

val genderDictionaries: List<GenderDict> by lazy {
    val genderTypeRegex = listOf(
            "Pria",
            "Wanita",
            "Laki-laki",
            "Laki2",
            "Perempuan"
    ).reduce { f, s -> "$f|$s" }

    listOf(
            GenderDict("($genderTypeRegex)")
    )
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
