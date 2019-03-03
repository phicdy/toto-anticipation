package com.phicdy.totoanticipation.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JLeagueRequestExecutor(private val service: JLeagueService) {

    interface JLeagueRequestCallback {
        fun onResponseJ1Ranking(response: Response<ResponseBody>)
        fun onFailureJ1Ranking(call: Call<ResponseBody>, throwable: Throwable)
        fun onResponseJ2Ranking(response: Response<ResponseBody>)
        fun onFailureJ2Ranking(call: Call<ResponseBody>, throwable: Throwable)
        fun onResponseJ3Ranking(response: Response<ResponseBody>)
        fun onFailureJ3Ranking(call: Call<ResponseBody>, throwable: Throwable)
    }

    fun fetchJ1Ranking(callback: JLeagueRequestCallback) {
        val j1Callback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onResponseJ1Ranking(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureJ1Ranking(call, throwable)
            }
        }
        val call = service.j1ranking()
        call.enqueue(j1Callback)
    }

    fun fetchJ2Ranking(callback: JLeagueRequestCallback) {
        val j2Callback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onResponseJ2Ranking(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureJ2Ranking(call, throwable)
            }
        }
        val call = service.j2ranking()
        call.enqueue(j2Callback)
    }

    fun fetchJ3Ranking(callback: JLeagueRequestCallback) {
        val j3Callback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.onResponseJ3Ranking(response)
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailureJ3Ranking(call, throwable)
            }
        }
        val call = service.j3ranking()
        call.enqueue(j3Callback)
    }
}
