package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.RakutenTotoInfoParser;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoTopParser;
import com.phicdy.totoanticipation.view.GameListView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GameListPresenter implements Presenter, RakutenTotoRequestExecutor.RakutenTotoRequestCallback {
    private GameListView view;
    private RakutenTotoRequestExecutor executor;

    public GameListPresenter(@NonNull RakutenTotoRequestExecutor executor) {
        this.executor = executor;
    }

    public void setView(GameListView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.startProgress();
        executor.fetchRakutenTotoTopPage(this);
    }

    @Override
    public void onResponseTotoTop(@NonNull Response<ResponseBody> response) {
        try {
            String body = response.body().string();
            String latestTotoNum = new RakutenTotoTopParser().latestTotoNumber(body);
            if (!latestTotoNum.equals("")) {
                view.setTitleFrom(latestTotoNum);
                executor.fetchRakutenTotoInfoPage(latestTotoNum, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.stopProgress();
        }
    }

    @Override
    public void onFailureTotoTop(Call<ResponseBody> call, Throwable throwable) {
        view.stopProgress();
    }

    @Override
    public void onResponseTotoInfo(@NonNull Response<ResponseBody> response) {
        view.stopProgress();
        try {
            String body = response.body().string();
            view.initListBy(new RakutenTotoInfoParser().games(body));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailureTotoInfo(Call<ResponseBody> call, Throwable throwable) {
        view.stopProgress();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
