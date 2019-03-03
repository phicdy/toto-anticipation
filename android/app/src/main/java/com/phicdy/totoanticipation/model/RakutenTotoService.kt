package com.phicdy.totoanticipation.model


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface RakutenTotoService {

    @GET("toto/schedule")
    fun schedule(): Call<ResponseBody>

    @GET("toto/schedule/{number}/")
    fun totoInfo(@Path("number") number: String): Call<ResponseBody>

    object Factory {
        fun create(): RakutenTotoService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://toto.rakuten.co.jp/")
                    .build()
            return retrofit.create(RakutenTotoService::class.java)
        }
    }
}

