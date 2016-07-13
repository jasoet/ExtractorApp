package id.jasoet.extractor.core.util.codec

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.StringUtils
import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable

/**
 * Documentation Here
 *
 * @author Deny Prasetyo
 */


private val base64Codec = Base64()


fun String.abbreviate(width: Int = 42): String {
    return org.apache.commons.lang3.StringUtils.abbreviate(this, width)
}

fun String.getBytesUtf8(): ByteArray {
    return StringUtils.getBytesUtf8(this)
}

fun <T : Serializable> T.serializeToByteArray(): ByteArray {
    return SerializationUtils.serialize(this)
}
