package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JLeagueRequestExecutor {

    public interface JLeagueRequestCallback {
        void onResponseJ1Ranking(@NonNull Response<ResponseBody> response);
        void onFailureJ1Ranking(Call<ResponseBody> call, Throwable throwable);
        void onResponseJ2Ranking(@NonNull Response<ResponseBody> response);
        void onFailureJ2Ranking(Call<ResponseBody> call, Throwable throwable);
    }
    private final JLeagueService service;
    public JLeagueRequestExecutor(@NonNull JLeagueService service) {
        this.service = service;
    }

    public void fetchJ1Ranking(@NonNull final JLeagueRequestCallback callback) {
        Callback<ResponseBody> j1Callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onResponseJ1Ranking(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailureJ1Ranking(call, throwable);
            }
        };
        Call<ResponseBody> call = service.j1ranking();
        call.enqueue(j1Callback);
    }

    public void fetchJ2Ranking(@NonNull final JLeagueRequestCallback callback) {
        Callback<ResponseBody> j2Callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onResponseJ2Ranking(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailureJ2Ranking(call, throwable);
            }
        };
        Call<ResponseBody> call = service.j2ranking();
        call.enqueue(j2Callback);
    }

}
