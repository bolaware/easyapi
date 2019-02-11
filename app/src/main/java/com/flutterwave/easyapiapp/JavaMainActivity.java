package com.flutterwave.easyapiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.flutterwave.easyapi.EasyApiUtils;
import com.flutterwave.easyapi.callbacks.Callbacks;
import com.flutterwave.easyapi.general.EasyApiCaller;
import com.flutterwave.easyapiapp.data.NewsResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import static com.flutterwave.easyapi.general.EasyApiCallerKt.GET;
import static com.flutterwave.easyapi.general.EasyApiCallerKt.POST;

public class JavaMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_main);

        get();
    }


    private void post(){
        try {
            final TextView textView = (TextView) findViewById(R.id.textTV);


            JSONObject obj = new JSONObject();
            obj.put("name", "John");
            obj.put("job", "Engineer");

            new EasyApiCaller(this)
                    .setMethod(POST, obj)
                    .setTimeOut(1)
                    .showLogResponse(true)
                    .setUrl("https://reqres.in/api/users")
                    .request()
                    .await(
                        new Callbacks.onResponse(){
                            @Override
                            public void onResponse(@NotNull String jsonAsString, @NotNull EasyApiCaller easyApiCaller) {
                                textView.setText(jsonAsString);
                            }

                            @Override
                            public void onFailure(@Nullable String errorResponse, int responseCode) {
                                textView.setText(errorResponse);
                            }
                        }
                    );
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    private void get(){
        final TextView textView = findViewById(R.id.textTV);

        new EasyApiCaller(this)
                .setMethod(GET)
                .setTimeOut(1)
                .showLogResponse(true)
                .setUrl("https://newsapi.org/v2/everything?q=bitcoin&from=2019-02-05&sortBy=publishedAt&apiKey=8d1024a54b9b473e930770f97189febe")
                .request()
                .await(
                        new Callbacks.onResponse(){
                            @Override
                            public void onResponse(@NotNull String jsonAsString, @NotNull EasyApiCaller easyApiCaller) {
                                NewsResponse newsResponse = EasyApiUtils.convertFromJson(jsonAsString, NewsResponse.class);
                                Toast.makeText(JavaMainActivity.this, newsResponse.getArticles().size() + " articles fetched", Toast.LENGTH_LONG).show();
                                textView.setText(jsonAsString);
                            }

                            @Override
                            public void onFailure(@Nullable String errorResponse, int responseCode) {
                                textView.setText(errorResponse);
                            }
                        }
                );
    }

}
