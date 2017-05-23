package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.JLeagueRequestExecutor;
import com.phicdy.totoanticipation.model.JLeagueService;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoService;
import com.phicdy.totoanticipation.model.TestRakutenTotoInfoPage;
import com.phicdy.totoanticipation.model.TestRakutenTotoPage;
import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.view.GameListView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GameListPresenterTest {

    private GameListPresenter presenter;
    private RakutenTotoRequestExecutor rakutenTotoRequestExecutor;
    private JLeagueRequestExecutor jLeagueRequestExecutor;
    private MockView view;
    private GameListStorage storage;

    @Before
    public void setup() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        rakutenTotoRequestExecutor = new RakutenTotoRequestExecutor(service);
        JLeagueService service1 = JLeagueService.Factory.create();
        jLeagueRequestExecutor = new JLeagueRequestExecutor(service1);
        storage = new MockStorage();
        presenter = new GameListPresenter(rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage);
        view = new MockView();
        presenter.setView(view);
    }

    @Test
    public void progressBarStartsAfterOnCreate() {
        presenter.onCreate();
        assertTrue(view.isProgressing);
    }

    @Test
    public void testOnResume() {
        // For coverage
        presenter.onResume();
    }

    @Test
    public void testOnPause() {
        // For coverage
        presenter.onPause();
    }

    @Test
    public void progressBarStopsWhenOnFailureTotoTop() {
        presenter.onFailureTotoTop(null, null);
        assertFalse(view.isProgressing);
    }

    @Test
    public void progressBarStopsWhenOnFailureTotoInfo() {
        presenter.onFailureTotoInfo(null, null);
        assertFalse(view.isProgressing);
    }

    @Test
    public void titleBecomesLatestTotoAfterReceivingTotoTopResponse() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        presenter.onResponseTotoTop(response);
        assertThat(view.title, is("第0923回"));
    }

    @Test
    public void listIsSetWhenStoredListExists() {
        ArrayList<Game> testList = new ArrayList<>();
        testList.add(new Game("home", "away"));
        storage.store("0923", testList);
        presenter = new GameListPresenter(rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage);
        MockView view = new MockView();
        presenter.setView(view);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        presenter.onResponseTotoTop(response);
        assertThat(presenter.gameAt(0).homeTeam, is("home"));
    }

    @Test
    public void progressBarStopsWhenStoredListExists() {
        ArrayList<Game> testList = new ArrayList<>();
        testList.add(new Game("home", "away"));
        storage.store("0923", testList);
        presenter = new GameListPresenter(rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage);
        MockView view = new MockView();
        presenter.setView(view);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        presenter.onResponseTotoTop(response);
        assertFalse(view.isProgressing);
    }

    @Test
    public void titleDoesNotChangeAfterReceivingInvalidTotoTopResponse() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), "<html><body>hoge</body></html>"));
        presenter.onResponseTotoTop(response);
        assertThat(view.title, is("toto予想"));
    }

    @Test
    public void gamesAreSetAfterReceivingTotoInfoResponse() {
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
        for (int i = 0; i < presenter.gameSize(); i++) {
            assertThat(presenter.gameAt(i).homeTeam, is(expectedGames.get(i).homeTeam));
            assertThat(presenter.gameAt(i).awayTeam, is(expectedGames.get(i).awayTeam));
        }
    }

    @Test
    public void gamesAreEmptyAfterReceivingInvalidTotoInfoResponse() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), "<html><body>hoge</body></html>"));
        presenter.onResponseTotoInfo(response);
        assertThat(presenter.gameSize(), is(0));
    }

    @Test
    public void anticipationBecomesHomeWhenHomeRadioButtonIsClicked() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onHomeRadioButtonClicked(0, true);
        assertThat(presenter.gameAt(0).anticipation, is(Game.Anticipation.HOME));
    }

    @Test
    public void anticipationBecomesAwayWhenAwayRadioButtonIsClicked() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onAwayRadioButtonClicked(0, true);
        assertThat(presenter.gameAt(0).anticipation, is(Game.Anticipation.AWAY));
    }

    @Test
    public void anticipationBecomesDrawWhenDrawRadioButtonIsClicked() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onDrawRadioButtonClicked(0, true);
        assertThat(presenter.gameAt(0).anticipation, is(Game.Anticipation.DRAW));
    }

    @Test
    public void clickMinusPositionOfRadioButtonHasNoAffect() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onAwayRadioButtonClicked(-1, true);
        // All of the anticipations are default, HOME
        for (int i = 0; i < presenter.gameSize(); i++) {
            assertThat(presenter.gameAt(i).anticipation, is(Game.Anticipation.HOME));
        }
    }

    @Test
    public void clickBiggerPositionThanGameSizeOfRadioButtonHasNoAffect() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onAwayRadioButtonClicked(presenter.gameSize()+1, true);
        // All of the anticipations are default, HOME
        for (int i = 0; i < presenter.gameSize(); i++) {
            assertThat(presenter.gameAt(i).anticipation, is(Game.Anticipation.HOME));
        }
    }

    @Test
    public void WhenFabIsClickedTotoAnticipationActivityStarts() {
        presenter.onFabClicked();
        assertTrue(view.isTotoAnticipationActivityStarted);
    }

    private class MockView implements GameListView {

        private String title = "toto予想";
        private boolean isProgressing = false;
        private boolean isTotoAnticipationActivityStarted = false;

        @Override
        public void initList() {
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

        @Override
        public void startTotoAnticipationActivity(@NonNull String totoNum) {
            isTotoAnticipationActivityStarted = true;
        }
    }

    private class MockStorage implements GameListStorage {

        private String totoNum;
        private List<Game> games;

        @Override
        public String totoNum() {
            return null;
        }

        @Override
        public List<Game> list(@NonNull String totoNum) {
            return games;
        }

        @Override
        public void store(@NonNull String totoNum, @NonNull List<Game> list) {
            this.totoNum = totoNum;
            games = list;
        }
    }
}
