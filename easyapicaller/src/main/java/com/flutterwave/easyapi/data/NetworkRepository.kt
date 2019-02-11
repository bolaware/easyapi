package com.flutterwave.easyapi.data

import com.flutterwave.easyapi.general.GET
import com.flutterwave.easyapi.general.POST
import com.flutterwave.easyapi.general.PUT
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.net.URL
import java.util.concurrent.TimeUnit

class NetworkRepository(val timeOut : Long = 1, val logResponse : Boolean = false, val url : String){


    val okHttpClient by lazy {
        providesOkHttpClient()
    }

    val host by lazy {
        URL(url).host
    }

    val retrofit by lazy {
        providesRetrofit(okHttpClient)
    }

    val service by lazy {
        providesApiService(retrofit)
    }

    private fun providesApiService(retrofit: Retrofit): ApiService2 {
        return retrofit.create(ApiService2::class.java)
    }

    private fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.MINUTES)
                .writeTimeout(timeOut, TimeUnit.MINUTES)
                .connectTimeout(timeOut, TimeUnit.MINUTES)
                .apply {
                    if (logResponse) {
                        val logging = HttpLoggingInterceptor()
                        logging.level = HttpLoggingInterceptor.Level.BODY
                        addNetworkInterceptor(logging)
                    }
                }
                .build()
    }

    private fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()

        return Retrofit.Builder()
                .baseUrl("https://${host}/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    fun fetch(body  : JSONObject, method : String) : Deferred<Response<ResponseBody>>?{
        when(method){
            PUT -> {

            }
            GET -> {
                return service.genericGET(url)
            }
            POST -> {
                return service.genericPOST(url, body)
            }
        }
        return null
    }


    interface ApiService2 {
        @GET
        fun genericGET(@Url url: String) : Deferred<Response<ResponseBody>>

        @POST
        fun genericPOST(@Url url: String, @Body body : JSONObject) : Deferred<Response<ResponseBody>>
    }
}

