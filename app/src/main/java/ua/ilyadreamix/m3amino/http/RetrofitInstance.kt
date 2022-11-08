package ua.ilyadreamix.m3amino.http

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.ilyadreamix.m3amino.http.interceptor.AminoRequestInterceptor
import java.util.concurrent.TimeUnit

// TODO: Singleton
object RetrofitInstance {
    private const val BASE_URL = "https://service.narvii.com/api/v1/"

    private lateinit var client: OkHttpClient
    private lateinit var retrofit: Retrofit

    fun getRetrofitInstance(acceptLanguage: String, ndcLang: String): Retrofit {
        return if (this::retrofit.isInitialized) {
            Log.d("RetrofitInstance", "Got Retrofit instance")
            retrofit
        } else {
            Log.d("RetrofitInstance", "Created new Retrofit instance")

            val requestInterceptor = AminoRequestInterceptor(acceptLanguage, ndcLang)
            val logInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            client = OkHttpClient.Builder()
                .apply {
                    this.addInterceptor(requestInterceptor)
                    this.addInterceptor(logInterceptor)
                        .connectTimeout(20, TimeUnit.SECONDS)
                }
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            return retrofit
        }
    }
}