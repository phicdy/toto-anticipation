package com.phicdy.totoanticipation.api


import okhttp3.ResponseBody
import retrofit2.http.GET

interface JLeagueService {

    @GET("standings/j1/")
    suspend fun j1ranking(): ResponseBody

    @GET("standings/j2/")
    suspend fun j2ranking(): ResponseBody

    @GET("standings/j3/")
    suspend fun j3ranking(): ResponseBody
}

