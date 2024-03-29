package com.phicdy.totoanticipation.api


import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface RakutenTotoService {

    @GET("toto/schedule//?l-id=header_pc_sub_toto_schedule")
    suspend fun schedule(): ResponseBody

    @GET("toto/schedule/{number}/")
    suspend fun totoInfo(@Path("number") number: String): ResponseBody
}

