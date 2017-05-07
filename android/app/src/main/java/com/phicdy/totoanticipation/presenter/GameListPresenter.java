package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.model.RakutenTotoInfoParser;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoTopParser;
import com.phicdy.totoanticipation.view.GameListView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GameListPresenter implements Presenter, RakutenTotoRequestExecutor.RakutenTotoRequestCallback {
    private GameListView view;
    private RakutenTotoRequestExecutor executor;
    private GameListStorage storage;
    private String totoNum;
    private List<Game> games;

    public GameListPresenter(@NonNull RakutenTotoRequestExecutor executor,
                             @NonNull GameListStorage storage) {
        this.executor = executor;
        this.storage = storage;
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
            totoNum = new RakutenTotoTopParser().latestTotoNumber(body);
            if (totoNum.equals("")) {
                view.stopProgress();
                return;
            }
            view.setTitleFrom(totoNum);
            games = storage.list(totoNum);
            if (games == null || games.size() == 0) {
                executor.fetchRakutenTotoInfoPage(totoNum, this);
            } else {
                view.stopProgress();
                view.initList();
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
            games = new RakutenTotoInfoParser().games(body);
            view.initList();
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

    public void onHomeRadioButtonClicked(int position, boolean isChecked) {
        onRadioButtonClicked(position, isChecked, Game.Anticipation.HOME);
    }

    public void onAwayRadioButtonClicked(int position, boolean isChecked) {
        onRadioButtonClicked(position, isChecked, Game.Anticipation.AWAY);
    }

    public void onDrawRadioButtonClicked(int position, boolean isChecked) {
        onRadioButtonClicked(position, isChecked, Game.Anticipation.DRAW);
    }

    private void onRadioButtonClicked(int position, boolean isChecked, Game.Anticipation anticipation) {
        if (games == null || position >= games.size() || position < 0) return;
        if (isChecked ) {
            games.get(position).anticipation = anticipation;
            storage.store(totoNum, games);
        }
    }

    public Game gameAt(int position) {
        if (games == null || position >= games.size() || position < 0) return null;
        return games.get(position);
    }

    public int gameSize() {
        return games == null ? 0 : games.size();
    }
}
