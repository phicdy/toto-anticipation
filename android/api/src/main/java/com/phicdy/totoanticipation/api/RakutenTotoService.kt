package com.phicdy.totoanticipation.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RakutenTotoService {

    @GET("toto/schedule")
    fun schedule(): Call<ResponseBody>

    @GET("toto/schedule/{number}/")
    fun totoInfo(@Path("number") number: String): Call<ResponseBody>
}

