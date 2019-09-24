package com.phicdy.totoanticipation.legacy.model


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

interface JLeagueService {

    @GET("standings/j1/")
    fun j1ranking(): Call<ResponseBody>

    @GET("standings/j2/")
    fun j2ranking(): Call<ResponseBody>

    @GET("standings/j3/")
    fun j3ranking(): Call<ResponseBody>

    object Factory {
        fun create(): JLeagueService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.jleague.jp/")
                    .build()
            return retrofit.create(JLeagueService::class.java)
        }
    }
}
