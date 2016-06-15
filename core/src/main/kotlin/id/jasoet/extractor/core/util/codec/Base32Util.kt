package id.jasoet.extractor.core.util.codec

import kotlinslang.control.Try
import kotlinslang.control.failure
import kotlinslang.control.recoverWith
import kotlinslang.control.tryOf
import org.apache.commons.codec.binary.Base32
import org.apache.commons.codec.binary.StringUtils
import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable


/**
 * [TODO: Documentation]
 *
 * @author Deny Prasetyo.
 */

private val base32Codec = Base32()

fun String.base32Encode(): String {
    return base32Codec.encodeToString(this.getBytesUtf8())
}

fun String.base32EncodeToByteArray(): ByteArray {
    return base32Codec.encode(this.getBytesUtf8())
}


fun <T : Serializable> T.base32Encode(): String {
    return base32Codec.encodeToString(this.serializeToByteArray())
}

fun <T : Serializable> T.base32EncodeToByteArray(): ByteArray {
    return base32Codec.encode(this.serializeToByteArray())
}

fun ByteArray.base32EncodeToString(): String {
    return base32Codec.encodeAsString(this)
}

fun ByteArray.base32Encode(): ByteArray {
    return base32Codec.encode(this)
}

fun String.base32Decode(): Try<String> {
    return tryOf { StringUtils.newStringUtf8(base32Codec.decode(this)) }
        .recoverWith { failure(DecodeBase32Exception("Exception when Decode ${this.abbreviate()} ", it)) }
}

fun <T : Serializable> String.base32DecodeToObject(): Try<T> {
    return tryOf { SerializationUtils.deserialize<T>(base32Codec.decode(this)) }
        .recoverWith { failure(DecodeBase32Exception("Exception when Decode ${this.abbreviate()} ", it)) }
}

fun <T : Serializable> ByteArray.base32DecodeToObject(): Try<T> {
    return tryOf { SerializationUtils.deserialize<T>(base32Codec.decode(this)) }
        .recoverWith { failure(DecodeBase32Exception("Exception when Decode ${this.size} bytes ", it)) }
}


class DecodeBase32Exception(message: String, cause: Throwable) : Exception(message, cause)