package com.phicdy.totoanticipation.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RakutenTotoRequestExecutor(private val service: RakutenTotoService) {

    interface RakutenTotoRequestCallback {
        fun onResponseTotoTop(response: Response<ResponseBody>)
        fun onFailureTotoTop(call: Call<ResponseBody>, throwable: Throwable)
        fun onResponseTotoInfo(response: Response<ResponseBody>)
        fun onFailureTotoInfo(call: Call<ResponseBody>, throwable: Throwable)
    }

    fun fetchRakutenTotoTopPage(callback: RakutenTotoRequestCallback) {
        val topPageCallback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                callback.onResponseTotoTop(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureTotoTop(call, throwable)
            }
        }
        val call = service.schedule()
        call.enqueue(topPageCallback)
    }

    fun fetchRakutenTotoInfoPage(num: String,
                                 callback: RakutenTotoRequestCallback) {
        val rakutenTotoInfoPageCallback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                callback.onResponseTotoInfo(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureTotoInfo(call, throwable)
            }
        }
        val call = service.totoInfo(num)
        call.enqueue(rakutenTotoInfoPageCallback)
    }

}
