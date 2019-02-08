package com.flutterwave.easyapi.callbacks

import com.flutterwave.easyapi.general.EasyApiCaller

class Callbacks{

    interface onResponse{
        fun onResponse(jsonAsString : String, easyApiCaller: EasyApiCaller)
        fun onFailure(errorResponse : String? , responseCode : Int)
    }
}