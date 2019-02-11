package com.flutterwave.easyapiapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.flutterwave.easyapi.callbacks.Callbacks
import com.flutterwave.easyapi.general.EasyApiCaller
import com.flutterwave.easyapi.general.GET
import com.flutterwave.easyapi.general.POST
import com.flutterwave.easyapiapp.data.NewsResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get()

        post()
    }

    private fun get(){
        var newsResponse: NewsResponse?
        EasyApiCaller(this)
                .method(GET)
                .timeOut(1)
                .logResponse(true)
                .url("https://newsapi.org/v2/everything?q=bitcoin&from=2019-02-05&sortBy=publishedAt&apiKey=8d1024a54b9b473e930770f97189febe")
                .request()
                .await(object : Callbacks.onResponse {
                    override fun onResponse(jsonAsString: String, easyApiCaller: EasyApiCaller) {
                        newsResponse = easyApiCaller.convertFromJSON<NewsResponse>(jsonAsString)
                        textTV.text = newsResponse?.let {
                            it.articles.size.toString()
                        }
                    }

                    override fun onFailure(errorResponse: String?, responseCode: Int) {
                        textTV.text = errorResponse
                    }
                })
    }

    private fun post(){

        val obj = JSONObject()
        obj.put("name", "Bolaji")
        obj.put("job", "Engineer")

        EasyApiCaller(this)
                .method(POST, obj)
                .timeOut(1)
                .logResponse(true)
                .url("https://reqres.in/api/users")
                .request()
                .await(object : Callbacks.onResponse {
                    override fun onResponse(jsonAsString: String, easyApiCaller: EasyApiCaller) {
                        textTV.text = jsonAsString
                    }

                    override fun onFailure(errorResponse: String?, responseCode: Int) {
                        textTV.text = errorResponse
                    }
                })
    }

}



