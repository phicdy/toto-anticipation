package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoService;
import com.phicdy.totoanticipation.model.TestRakutenTotoInfoPage;
import com.phicdy.totoanticipation.model.TestRakutenTotoPage;
import com.phicdy.totoanticipation.view.GameListView;

import org.junit.Test;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GameListPresenterTest {

    @Test
    public void progressBarStartsAfterOnCreate() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        presenter.onCreate();
        assertTrue(view.isProgressing);
    }

    @Test
    public void testOnResume() {
        // For coverage
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        presenter.onResume();
    }

    @Test
    public void testOnPause() {
        // For coverage
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        presenter.onPause();
    }

    @Test
    public void progressBarStopsWhenOnFailureTotoTop() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        presenter.onFailureTotoTop(null, null);
        assertFalse(view.isProgressing);
    }

    @Test
    public void progressBarStopsWhenOnFailureTotoInfo() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        presenter.onFailureTotoInfo(null, null);
        assertFalse(view.isProgressing);
    }

    @Test
    public void titleBecomesLatestTotoAfterReceivingTotoTopResponse() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        presenter.onResponseTotoTop(response);
        assertThat(view.title, is("第0923回"));
    }

    @Test
    public void titleDoesNotChangeAfterReceivingInvalidTotoTopResponse() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), "<html><body>hoge</body></html>"));
        presenter.onResponseTotoTop(response);
        assertThat(view.title, is("toto予想"));
    }

    @Test
    public void gamesAreSetAfterReceivingTotoInfoResponse() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        ArrayList<Game> expectedGames = new ArrayList<>();
        expectedGames.add(new Game("浦和", "札幌"));
        expectedGames.add(new Game("甲府", "Ｃ大阪"));
        expectedGames.add(new Game("広島", "仙台"));
        expectedGames.add(new Game("鹿島", "磐田"));
        expectedGames.add(new Game("柏", "横浜Ｍ"));
        expectedGames.add(new Game("新潟", "Ｆ東京"));
        expectedGames.add(new Game("鳥栖", "神戸"));
        expectedGames.add(new Game("名古屋", "山口"));
        expectedGames.add(new Game("横浜Ｃ", "千葉"));
        expectedGames.add(new Game("京都", "松本"));
        expectedGames.add(new Game("愛媛", "長崎"));
        expectedGames.add(new Game("東京Ｖ", "群馬"));
        expectedGames.add(new Game("町田", "徳島"));
        for (int i = 0; i < view.games.size(); i++) {
            assertThat(view.games.get(i).homeTeam, is(expectedGames.get(i).homeTeam));
            assertThat(view.games.get(i).awayTeam, is(expectedGames.get(i).awayTeam));
        }
    }

    @Test
    public void gamesAreEmptyAfterReceivingInvalidTotoInfoResponse() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        GameListPresenter presenter = new GameListPresenter(executor);
        MockView view = new MockView();
        presenter.setView(view);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), "<html><body>hoge</body></html>"));
        presenter.onResponseTotoInfo(response);
        assertThat(view.games.size(), is(0));
    }

    private class MockView implements GameListView {

        private String title = "toto予想";
        private ArrayList<Game> games;
        private boolean isProgressing = false;

        @Override
        public void initListBy(@NonNull ArrayList<Game> games) {
            this.games = games;
        }

        @Override
        public void setTitleFrom(@NonNull String xxTh) {
            title = "第" + xxTh + "回";
        }

        @Override
        public void startProgress() {
            isProgressing = true;
        }

        @Override
        public void stopProgress() {
            isProgressing = false;
        }
    }
}
