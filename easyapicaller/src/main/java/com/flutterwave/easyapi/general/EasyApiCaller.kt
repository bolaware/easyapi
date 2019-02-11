package com.flutterwave.easyapi.general


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.flutterwave.easyapi.callbacks.Callbacks
import com.flutterwave.easyapi.data.NetworkRepository
import com.flutterwave.easyapi.data.Resource
import com.flutterwave.easyapi.data.Status
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

const val POST = "post"
const val GET = "get"

class EasyApiCaller(val activity: AppCompatActivity) {

    private val repo by lazy {
        NetworkRepository(timeOut, true, url)
    }

    private var deferredResponse: Deferred<Response<ResponseBody>>? = null

    val gson: Gson by lazy {
        Gson()
    }

    private lateinit var method: String

    private var timeOut: Long = 1

    private var logResponse: Boolean = false

    private lateinit var url : String

    private var body = JSONObject()


    fun timeOut(timeOut : Long): EasyApiCaller {
        this.timeOut = timeOut
        return this
    }

    fun logResponse(logResponse : Boolean): EasyApiCaller {
        this.logResponse = logResponse
        return this
    }

    fun url(url : String) : EasyApiCaller {
        this.url = url
        return this
    }

    fun method(method : String) : EasyApiCaller {
        this.method = method
        return this
    }

    fun method(method : String, body: JSONObject) : EasyApiCaller {
        this.method = method
        this.body = body
        return this
    }


    fun request(): EasyApiCaller {
        deferredResponse = repo.fetch(body, method)
        return this
    }

    fun await(callbacks: Callbacks.onResponse): EasyApiCaller {

        val getResponseLD: MediatorLiveData<Resource<Any>> = MediatorLiveData()
        getResponseLD.observe(activity, Observer {
        })
        val result = awaitCall(deferredResponse)
        getResponseLD.addSource(result) {
            when(it?.status){
                Status.SUCCESS -> {
                    it?.data?.string()?.let {jsonAsString ->
                        //getValueLD.value = jsonAsString
                        callbacks.onResponse(jsonAsString, this)
                    }
                }
                Status.ERROR -> {
                    it?.let {
                        callbacks.onFailure(it.message ,it.code)
                    }
                }
            }


        }
        return this
    }


    private fun <ResponseType>
            awaitCall(deferredResponse: Deferred<Response<ResponseType>>?): LiveData<Resource<ResponseType>> {

        val result = MutableLiveData<Resource<ResponseType>>()

        try {
            async(CommonPool) {

                deferredResponse?.let {
                    val response = it.await()
                    if (response.isSuccessful) {
                        withContext(UI) {
                            result.postValue(Resource(Status.SUCCESS, response.body(), response.body().toString()))
                        }
                    } else {
                        //todo add timeout check
                        withContext(UI) {
                            result.postValue(Resource(Status.ERROR, response.body(), response.errorBody()?.string(), response.code()))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //todo add timeout check or something
            result.postValue(Resource(Status.ERROR, null, "An network err"))
        }
        return result
    }


    inline fun <reified T> convertFromJSON(json : String) : T?{
        return try {
            val type = object : TypeToken<T>() {}.type
            gson.fromJson<T>(json, type)

        } catch (e: Exception) {
            Log.d("fetched error", e.message)
            e.printStackTrace()
            null
        }
    }
}