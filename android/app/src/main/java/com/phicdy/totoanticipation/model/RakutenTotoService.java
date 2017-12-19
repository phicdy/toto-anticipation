package com.phicdy.totoanticipation.model;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    }
}

