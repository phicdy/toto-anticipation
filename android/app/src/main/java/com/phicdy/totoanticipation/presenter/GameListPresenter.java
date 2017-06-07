package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.JLeagueRankingParser;
import com.phicdy.totoanticipation.model.JLeagueRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoInfoParser;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoTopParser;
import com.phicdy.totoanticipation.model.TeamInfoMapper;
import com.phicdy.totoanticipation.model.Toto;
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.view.GameListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GameListPresenter implements Presenter, RakutenTotoRequestExecutor.RakutenTotoRequestCallback,
        JLeagueRequestExecutor.JLeagueRequestCallback{
    private GameListView view;
    private RakutenTotoRequestExecutor rakutenTotoRequestExecutor;
    private JLeagueRequestExecutor jLeagueRequestExecutor;
    private GameListStorage storage;
    private Toto toto;
    private List<Game> games;
    private Map<String, Integer> j1ranking = new HashMap<>();
    private Map<String, Integer> j2ranking = new HashMap<>();

    public GameListPresenter(@NonNull RakutenTotoRequestExecutor rakutenTotoRequestExecutor,
                             @NonNull JLeagueRequestExecutor jLeagueRequestExecutor,
                             @NonNull GameListStorage storage) {
        this.rakutenTotoRequestExecutor = rakutenTotoRequestExecutor;
        this.jLeagueRequestExecutor = jLeagueRequestExecutor;
        this.storage = storage;
    }

    public void setView(GameListView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.startProgress();
        rakutenTotoRequestExecutor.fetchRakutenTotoTopPage(this);
    }

    @Override
    public void onResponseTotoTop(@NonNull Response<ResponseBody> response) {
        try {
            String body = response.body().string();
            toto = new RakutenTotoTopParser().latestToto(body);
            if (toto == null || toto.number.equals("")) {
                view.stopProgress();
                return;
            }
            view.setTitleFrom(toto.number);
            games = storage.list(toto.number);
            if (games == null || games.size() == 0) {
                jLeagueRequestExecutor.fetchJ1Ranking(this);
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
    public void onResponseJ1Ranking(@NonNull Response<ResponseBody> response) {
        try {
            String body = response.body().string();
            j1ranking = new JLeagueRankingParser().ranking(body);
            jLeagueRequestExecutor.fetchJ2Ranking(this);
        } catch (IOException e) {
            e.printStackTrace();
            view.stopProgress();
        }
    }

    @Override
    public void onFailureJ1Ranking(Call<ResponseBody> call, Throwable throwable) {
        view.stopProgress();
    }

    @Override
    public void onResponseJ2Ranking(@NonNull Response<ResponseBody> response) {
        try {
            String body = response.body().string();
            j2ranking = new JLeagueRankingParser().ranking(body);
            rakutenTotoRequestExecutor.fetchRakutenTotoInfoPage(toto.number, this);
        } catch (IOException e) {
            e.printStackTrace();
            view.stopProgress();
        }
    }

    @Override
    public void onFailureJ2Ranking(Call<ResponseBody> call, Throwable throwable) {
        view.stopProgress();
    }

    @Override
    public void onResponseTotoInfo(@NonNull Response<ResponseBody> response) {
        view.stopProgress();
        try {
            String body = response.body().string();
            games = new RakutenTotoInfoParser().games(body);
            for (Game game : games) {
                String homeFullName = TeamInfoMapper.fullNameForJLeagueRanking(game.homeTeam);
                String awayFullName = TeamInfoMapper.fullNameForJLeagueRanking(game.awayTeam);
                Integer homeRank = j1ranking.get(homeFullName);
                Integer awayRank = j1ranking.get(awayFullName);
                if (homeRank == null || awayRank == null) {
                    homeRank = j2ranking.get(homeFullName);
                    awayRank = j2ranking.get(awayFullName);
                }
                if (homeRank == null || awayRank == null) {
                    continue;
                }
                game.homeRanking = String.valueOf(homeRank);
                game.awayRanking = String.valueOf(awayRank);
            }
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
            if (toto != null) storage.store(toto, games);
        }
    }

    public Game gameAt(int position) {
        if (games == null || position >= games.size() || position < 0) return null;
        return games.get(position);
    }

    public int gameSize() {
        return games == null ? 0 : games.size();
    }

    public void onFabClicked() {
        if (toto == null) return;
        storage.store(toto, games);
        view.startTotoAnticipationActivity(toto.number);
    }

    public void onOptionsSettingSelected() {
        view.goToSetting();
    }
}
