package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RakutenTotoRequestExecutor {

    public interface RakutenTotoRequestCallback {
        void onResponseTotoTop(@NonNull Response<ResponseBody> response);
        void onFailureTotoTop(Call<ResponseBody> call, Throwable throwable);
        void onResponseTotoInfo(@NonNull Response<ResponseBody> response);
        void onFailureTotoInfo(Call<ResponseBody> call, Throwable throwable);
    }
    private final RakutenTotoService service;
    public RakutenTotoRequestExecutor(@NonNull RakutenTotoService service) {
        this.service = service;
    }

    public void fetchRakutenTotoTopPage(@NonNull final RakutenTotoRequestCallback callback) {
        Callback<ResponseBody> topPageCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                callback.onResponseTotoTop(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailureTotoTop(call, throwable);
            }
        };
        Call<ResponseBody> call = service.schedule();
        call.enqueue(topPageCallback);
    }

    public void fetchRakutenTotoInfoPage(@NonNull String num,
                                         @NonNull final RakutenTotoRequestCallback callback) {
        Callback<ResponseBody> rakutenTotoInfoPageCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                callback.onResponseTotoInfo(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailureTotoInfo(call, throwable);
            }
        };
        Call<ResponseBody> call = service.totoInfo(num);
        call.enqueue(rakutenTotoInfoPageCallback);
    }

}
