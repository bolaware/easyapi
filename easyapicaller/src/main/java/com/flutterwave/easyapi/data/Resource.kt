package com.flutterwave.easyapi.data

/**
 * Created by hfetuga on 04/08/2018.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?, val code : Int = 0) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}