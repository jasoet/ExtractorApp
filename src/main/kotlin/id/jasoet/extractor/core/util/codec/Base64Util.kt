package id.jasoet.extractor.core.util.codec

import kotlinslang.control.Try
import kotlinslang.control.failure
import kotlinslang.control.recoverWith
import kotlinslang.control.tryOf
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.StringUtils
import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable


/**
 * Documentation
 *
 * @author Deny Prasetyo.
 */

private val base64Codec = Base64()


fun String.base64Encode(): String {
    return base64Codec.encodeToString(this.getBytesUtf8())
}

fun String.base64EncodeToByteArray(): ByteArray {
    return base64Codec.encode(this.getBytesUtf8())
}

fun <T : Serializable> T.base64Encode(): String {
    return base64Codec.encodeToString(this.serializeToByteArray())
}

fun <T : Serializable> T.base64EncodeToByteArray(): ByteArray {
    return base64Codec.encode(this.serializeToByteArray())
}

fun ByteArray.base64EncodeToString(): String {
    return base64Codec.encodeAsString(this)
}

fun ByteArray.base64Encode(): ByteArray {
    return base64Codec.encode(this)
}


fun String.base64Decode(): Try<String> {
    return tryOf { StringUtils.newStringUtf8(base64Codec.decode(this)) }
        .recoverWith { failure(DecodeBase64Exception("Exception when Decode ${this.abbreviate()} ", it)) }
}

fun <T : Serializable> String.base64DecodeToObject(): Try<T> {
    return tryOf { SerializationUtils.deserialize<T>(base64Codec.decode(this)) }
        .recoverWith { failure(DecodeBase64Exception("Exception when Decode ${this.abbreviate()} ", it)) }
}

fun <T : Serializable> ByteArray.base64DecodeToObject(): Try<T> {
    return tryOf { SerializationUtils.deserialize<T>(base64Codec.decode(this)) }
        .recoverWith { failure(DecodeBase64Exception("Exception when Decode ${this.size} bytes ", it)) }
}


class DecodeBase64Exception(message: String, cause: Throwable) : Exception(message, cause)