package id.jasoet.extractor.util

import id.jasoet.extractor.dictionary.DictionaryContext
import org.apache.commons.io.IOUtils

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

fun String.loadLocalResource(): List<String> {
    val titleStream = javaClass.getResourceAsStream(this)
    return titleStream.use {
        IOUtils.toString(it, "UTF-8")
                .split(DictionaryContext.separator)
                .filter { it.isNotEmpty() }
    }
}