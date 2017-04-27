package com.phicdy.totoanticipation.model;


import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RakutenTotoService {

    @GET("toto/schedule")
    Call<ResponseBody> schedule();

    @GET("toto/schedule/{number}/")
    Call<ResponseBody> totoInfo(@Path("number") String number);

    class Factory {
        public static RakutenTotoService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://toto.rakuten.co.jp/")
                    .build();
            return retrofit.create(RakutenTotoService.class);
        }
        public static RakutenTotoService create(@NonNull OkHttpClient client) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://toto.rakuten.co.jp/")
                    .client(client)
                    .build();
            return retrofit.create(RakutenTotoService.class);
        }
    }
}

