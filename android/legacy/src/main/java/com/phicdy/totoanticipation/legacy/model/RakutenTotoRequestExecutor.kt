package com.phicdy.totoanticipation.legacy.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RakutenTotoRequestExecutor(private val service: RakutenTotoService) {

    interface RakutenTotoRequestCallback {
        fun onResponseTotoTop(response: Response<ResponseBody>)
        fun onFailureTotoTop(throwable: Throwable)
        fun onResponseTotoInfo(response: Response<ResponseBody>)
        fun onFailureTotoInfo(throwable: Throwable)
    }

    fun fetchRakutenTotoTopPage(callback: RakutenTotoRequestCallback) {
        val topPageCallback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onResponseTotoTop(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureTotoTop(throwable)
            }
        }
        val call = service.schedule()
        call.enqueue(topPageCallback)
    }

    fun fetchRakutenTotoInfoPage(num: String,
                                 callback: RakutenTotoRequestCallback) {
        val rakutenTotoInfoPageCallback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onResponseTotoInfo(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureTotoInfo(throwable)
            }
        }
        val call = service.totoInfo(num)
        call.enqueue(rakutenTotoInfoPageCallback)
    }

}
