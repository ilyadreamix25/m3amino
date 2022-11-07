package ua.ilyadreamix.m3amino.http.utility

import org.apache.commons.codec.digest.HmacUtils
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang3.ArrayUtils
import java.util.*

/// DO NOT modify code like:
/// new String(Hex.encodeHex(value)) -> Hex.encodeHexString(value)
/// It will break the compatibility with older SDKs.
/// Remember that Apache Commons codec is stored in Android's /system/framework

object AminoRequestUtility {
    private const val DEVICE_KEY = "02B258C63559D8804321C5D5065AF320358D366F"
    private const val SIG_KEY = "F8E7A61AC3F725941E3AC7CAE2D688BE97F30B93"
    private const val AMINO_VERSION = "3.5.34745"

    private fun hmacSha1Hex(
        value: ByteArray,
        key: ByteArray
    ): String {
        val hmacUtils = HmacUtils(HmacAlgorithms.HMAC_SHA_1, key)
        val hmacDigest = hmacUtils.hmac(value)

        return String(Hex.encodeHex(hmacDigest))
    }

    private fun hmacSha1Digest(
        value: ByteArray,
        key: ByteArray
    ): ByteArray {
        val hmac = HmacUtils(HmacAlgorithms.HMAC_SHA_1, key)
        return hmac.hmac(value)
    }

    fun generateDeviceId(): String {
        val data = ByteArray(20)
        val key = Hex.decodeHex(DEVICE_KEY.toCharArray())

        Random().nextBytes(data)

        val mac = hmacSha1Hex(
            ArrayUtils.addAll(
                Hex.decodeHex("42".toCharArray()),
                *data
            ),
            key
        )

        return ("42" +
                String(Hex.encodeHex(data)) +
                mac).uppercase(Locale.getDefault())
    }

    fun generateSig(data: String): String {
        val byteData = data.toByteArray()
        val key = Hex.decodeHex(SIG_KEY.toCharArray())

        val mac = hmacSha1Digest(
            byteData, key
        )

        val b64Bytes = Base64.encodeBase64(
            ArrayUtils.addAll(
                Hex.decodeHex("42".toCharArray()),
                *mac
            )
        )

        return String(b64Bytes)
    }

    fun generateUserAgent(): String {
        var systemAgent = System.getProperty("http.agent") as String
        systemAgent = systemAgent.substring(0, systemAgent.length - 1)

        return "$systemAgent; com.narvii.amino.master/$AMINO_VERSION)"
    }
}