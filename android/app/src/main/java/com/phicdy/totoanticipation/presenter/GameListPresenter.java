package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.RakutenTotoInfoParser;
import com.phicdy.totoanticipation.model.RakutenTotoService;
import com.phicdy.totoanticipation.model.RakutenTotoTopParser;
import com.phicdy.totoanticipation.view.GameListView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class GameListPresenter implements Presenter {
    private GameListView view;
    private RakutenTotoService service;

    public GameListPresenter(@NonNull RakutenTotoService service) {
        this.service = service;
    }

    public void setView(GameListView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        fetchAndInitGameList();
    }

    private void fetchAndInitGameList() {
        final Callback<ResponseBody> lotteryPageCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String body = response.body().string();
                    view.initListBy(new RakutenTotoInfoParser().games(body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        };
        Callback<ResponseBody> topPageCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String body = response.body().string();
                    String latestTotoNum = new RakutenTotoTopParser().latestTotoNumber(body);
                    if (!latestTotoNum.equals("")) {
                        fetchRakutenTotoInfoPage(latestTotoNum, lotteryPageCallback);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        };
        fetchRakutenTotoTopPage(topPageCallback);
    }

    private void fetchRakutenTotoTopPage(@NonNull Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.schedule();
        call.enqueue(callback);
    }

    private void fetchRakutenTotoInfoPage(@NonNull String num, @NonNull Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.totoInfo(num);
        call.enqueue(callback);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
