package ua.ilyadreamix.m3amino.http.interceptor

import android.util.Log
import okhttp3.*
import okio.Buffer
import okio.internal.commonToUtf8String
import ua.ilyadreamix.m3amino.http.utility.AminoRequestUtility
import ua.ilyadreamix.m3amino.http.utility.AminoSessionUtility

class AminoRequestInterceptor(
    private val acceptLanguage: String,
    private val ndcLang: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionData = AminoSessionUtility.getSessionData()
        val request = chain.request()
        var builder = request.newBuilder()

        sessionData.sessionId?.let { builder.addHeader("NDCAUTH", "sid=$it") }
        sessionData.deviceId?.let { builder.addHeader("NDCDEVICEID", it) }

        builder.addHeader("NDCLANG", ndcLang)
        builder.addHeader("Accept-Language", acceptLanguage)
        builder.addHeader("User-Agent", AminoRequestUtility.generateUserAgent())
        builder = setSig(builder, request.body)

        val proceededRequest = builder.build()

        return chain.proceed(proceededRequest)
    }

    private fun setSig(builder: Request.Builder, body: RequestBody?): Request.Builder {
        body?.let {
            return builder.addHeader(
                "NDC-MSG-SIG",
                AminoRequestUtility.generateSig(getBodyBytes(body))
            )
        }

        return builder
    }

    @Suppress("unused")
    private fun logRequest(request: Request, bodyBytes: ByteArray) {
        var requestString = "------ REQUEST ------\n"
        requestString += "${request.method} ${request.url}\n"
        requestString += getHeadersLog(request.headers)
        requestString = addBodyLogIfAvailable(requestString, request.body, bodyBytes)
        requestString += "------ REQUEST ------"

        Log.i("AminoRequestInterceptor", requestString)
    }

    private fun addBodyLogIfAvailable(
        log: String,
        body: RequestBody?,
        bodyBytes: ByteArray
    ): String {
        var newLog = log

        body?.let {
            val subtype = it.contentType()!!.subtype
            val types = listOf("plain", "json")

            if (types.contains(subtype)) {
                newLog += bodyBytes.commonToUtf8String() + "\n"
            }
        }

        return newLog
    }

    private fun getHeadersLog(headers: Headers): String {
        var headersString = ""

        headers.forEach{ pair ->
            val (key, value) = pair
            headersString += "$key: $value\n"
        }

        return headersString
    }

    private fun getBodyBytes(body: RequestBody): ByteArray {
        val buffer = Buffer()
        body.writeTo(buffer)

        val bodyBytes = buffer.readByteArray()
        buffer.close()

        return bodyBytes
    }
}