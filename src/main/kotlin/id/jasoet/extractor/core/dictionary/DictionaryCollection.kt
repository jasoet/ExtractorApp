package id.jasoet.extractor.core.dictionary

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

val dictionaryMap: Map<DictionaryType, Dictionary> by lazy {
    mapOf(
        DictionaryType.KEY to keyDictionary,
        DictionaryType.VALUE to valueDictionary,
        DictionaryType.MONEY to moneyDictionary,
        DictionaryType.AGE to ageDictionary,
        DictionaryType.DAY to dayDictionary,
        DictionaryType.RELIGION to religionDictionary,
        DictionaryType.GENDER to genderDictionary,
        DictionaryType.CRIME to crimeDictionary,
        DictionaryType.CLAUSE to clauseDictionary,
        DictionaryType.DATE to dateDictionary,
        DictionaryType.TIME to timeDictionary,
        DictionaryType.VEHICLE_NUMBER to vehicleNumberDictionary
    )
}

val valueDictionary: Dictionary by lazy {
    object : Dictionary {
        override val type: DictionaryType = DictionaryType.VALUE
        override val regexes: List<Regex> = listOf(Regex(".+", RegexOption.IGNORE_CASE))
    }
}

val vehicleNumberDictionary: Dictionary by lazy {
    object : Dictionary {
        override val type: DictionaryType = DictionaryType.VEHICLE_NUMBER
        override val regexes: List<Regex> = listOf(Regex("\\s+\\w\\w\\s\\d\\d\\d\\d\\s\\w\\w\\s+", RegexOption.IGNORE_CASE))
    }
}

val keyDictionary: Dictionary by lazy {
    object : Dictionary {
        override val type: DictionaryType = DictionaryType.KEY
        override val regexes: List<Regex> = listOf(Regex("[\\/\\s\\w\\d\\.-]+s*:", RegexOption.IGNORE_CASE))
    }
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

val crimeDictionary: Dictionary by lazy {
    val crimeTypeRegex = DictionaryContext.crimes.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(Regex("($crimeTypeRegex)", RegexOption.IGNORE_CASE))
    }
}

val clauseDictionary: Dictionary by lazy {
    val clauseTypeRegex = DictionaryContext.clauses.reduce { f, s -> "$f|$s" }
    val juntoRegex = DictionaryContext.juntos.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(
            Regex("\\d\\d?\\d?\\s+($clauseTypeRegex)", RegexOption.IGNORE_CASE),
            Regex("\\d\\d?\\d?(\\s+|$clauseTypeRegex)?\\s+($juntoRegex)\\s+\\d\\d?\\d?\\s+($clauseTypeRegex)", RegexOption.IGNORE_CASE)
        )
    }
}

val dayDictionary: Dictionary by lazy {
    val dayNamesRegex = DictionaryContext.days.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(Regex("($dayNamesRegex)", RegexOption.IGNORE_CASE))
    }
}

val dateDictionary: Dictionary by lazy {
    val monthNamesRegex = DictionaryContext.months.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(Regex("\\d\\d?-\\d\\d?-\\d\\d\\d?\\d?", RegexOption.IGNORE_CASE),
            Regex("\\d\\d?\\s+($monthNamesRegex)\\s+\\d\\d\\d?\\d?", RegexOption.IGNORE_CASE))
    }
}

val timeDictionary: Dictionary by lazy {
    val timeZoneRegex: String = DictionaryContext.timeZones.reduce { f, s -> "$f|$s" }

    object : Dictionary {
        override val type: DictionaryType = DictionaryType.RELIGION
        override val regexes: List<Regex> = listOf(Regex("\\d\\d?.\\d\\d?\\s+($timeZoneRegex)", RegexOption.IGNORE_CASE))
    }
}
