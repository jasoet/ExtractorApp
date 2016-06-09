package id.jasoet.extractor.util

import id.jasoet.extractor.dictionary.DictionaryContext
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import java.io.InputStream

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */

/**
 * Load Text Content from Resources
 *
 * @return Content as Collection of Lines
 */

fun String.loadLocalResourceContent(): List<String> {
    val titleStream = javaClass.getResourceAsStream(this)
    return titleStream.use {
        IOUtils.toString(it, "UTF-8")
                .split(DictionaryContext.separator)
                .filter { it.isNotEmpty() }
    }
}

/**
 * Calculate MD5 Digest as Hexadecimal
 * Do not close [InputStream] after use nor reset
 * please handle it yourself
 *
 * @return Hexadecimal Digest from [InputStream] as String
 */
fun InputStream.md5HexDigest(): String {
    return DigestUtils.md5Hex(this)
}

/**
 * Calculate SHA1 Digest as Hexadecimal
 * Do not close [InputStream] after use nor reset
 * please handle it yourself
 *
 * @return Hexadecimal Digest from [InputStream] as String
 */
fun InputStream.sha1HexDigest(): String {
    return DigestUtils.sha1Hex(this)
}

/**
 * Load Resource as [InputStream]
 * Just wrap `javaClass.getResourceAsStream(input)`
 *
 * @return [InputStream] Resources
 */
fun String.loadResourceAsStream(): InputStream {
    return javaClass.getResourceAsStream(this)
}