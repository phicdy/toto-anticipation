package com.phicdy.totoanticipation.model;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface JLeagueService {

    @GET("standings/j1/")
    Call<ResponseBody> j1ranking();

    @GET("standings/j2/")
    Call<ResponseBody> j2ranking();

    @GET("standings/j3/")
    Call<ResponseBody> j3ranking();

    class Factory {
        public static JLeagueService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.jleague.jp/")
                    .build();
            return retrofit.create(JLeagueService.class);
        }
    }
}

