package com.flutterwave.easyapi;

import android.util.Log;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class EasyApiUtils {
    public static <T> T convertFromJson(String json, Class<T> classOfT){
        try {
            return new Gson().fromJson(json, (Type) classOfT);
        } catch (Exception e) {
            Log.d("fetched error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
