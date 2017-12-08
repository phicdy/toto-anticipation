package com.phicdy.totoanticipation.model;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TotoService {

    @Headers("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:52.0) Gecko/20100101 Firefox/52.0")
    @GET("toto/index.html")
    Call<ResponseBody> top();

    @Headers("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:52.0) Gecko/20100101 Firefox/52.0")
    @GET("dci/I/IPA/IPA01.do")
    Call<ResponseBody> lotteryInfo(@Query("op") String op, @Query("holdCntId") String holdCntId);

    @GET("toto/schedule")
    Call<ResponseBody> rakuten();

    class Factory {
        public static TotoService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.toto-dream.com/")
                    .build();
            return retrofit.create(TotoService.class);
        }
    }
}

class TestConverter<T> implements Converter<ResponseBody, T> {

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        return null;
    }
}
