package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.JLeagueRequestExecutor;
import com.phicdy.totoanticipation.model.JLeagueService;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoService;
import com.phicdy.totoanticipation.model.TestRakutenTotoInfoPage;
import com.phicdy.totoanticipation.model.TestRakutenTotoPage;
import com.phicdy.totoanticipation.model.Toto;
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.model.storage.SettingStorage;
import com.phicdy.totoanticipation.view.GameListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GameListPresenterTest {

    private GameListPresenter presenter;
    private RakutenTotoRequestExecutor rakutenTotoRequestExecutor;
    private JLeagueRequestExecutor jLeagueRequestExecutor;
    private MockView view;
    private GameListStorage storage;
    private SettingStorage settingStorage;
    private DeadlineAlarm alarm;

    @Before
    public void setup() {
        RakutenTotoService service = RakutenTotoService.Factory.create();
        rakutenTotoRequestExecutor = new RakutenTotoRequestExecutor(service);
        JLeagueService service1 = JLeagueService.Factory.INSTANCE.create();
        jLeagueRequestExecutor = new JLeagueRequestExecutor(service1);
        storage = new MockStorage();
        settingStorage = new MockSettingStorage();
        alarm = Mockito.mock(DeadlineAlarm.class);
        view = new MockView();
        presenter = new GameListPresenter(view, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage,
                settingStorage.isDeadlineNotify(), alarm);
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
        Call<ResponseBody> call = Mockito.mock(Call.class);
        presenter.onFailureTotoTop(call, new Throwable());
        assertFalse(view.isProgressing);
    }

    @Test
    public void progressBarStopsWhenOnFailureTotoInfo() {
        Call<ResponseBody> call = Mockito.mock(Call.class);
        presenter.onFailureTotoInfo(call, new Throwable());
        assertFalse(view.isProgressing);
    }

    @Test
    public void titleBecomesLatestTotoAfterReceivingTotoInfoResponse() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        Response<ResponseBody> infoResponse = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoTop(response);
        presenter.onResponseTotoInfo(infoResponse);
        assertThat(view.title, is("第0923回(~04/22 13:50)"));
    }

    @Test
    public void listIsSetWhenStoredListExists() {
        ArrayList<Game> testList = new ArrayList<>();
        testList.add(new Game("home", "away"));
        storage.store(new Toto("0923", new Date()), testList);
        MockView view = new MockView();
        presenter = new GameListPresenter(view, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage,
                settingStorage.isDeadlineNotify(), alarm);
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        presenter.onResponseTotoTop(response);
        assertThat(presenter.gameAt(0).getHomeTeam(), is("home"));
    }

    @Test
    public void progressBarStopsWhenStoredListExists() {
        ArrayList<Game> testList = new ArrayList<>();
        testList.add(new Game("home", "away"));
        storage.store(new Toto("0923", new Date()), testList);
        MockView view = new MockView();
        presenter = new GameListPresenter(view, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage,
                settingStorage.isDeadlineNotify(), alarm);
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
            assertThat(presenter.gameAt(i).getHomeTeam(), is(expectedGames.get(i).getHomeTeam()));
            assertThat(presenter.gameAt(i).getAwayTeam(), is(expectedGames.get(i).getAwayTeam()));
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
        assertThat(presenter.gameAt(0).getAnticipation(), is(Game.Anticipation.HOME));
    }

    @Test
    public void anticipationBecomesAwayWhenAwayRadioButtonIsClicked() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onAwayRadioButtonClicked(0, true);
        assertThat(presenter.gameAt(0).getAnticipation(), is(Game.Anticipation.AWAY));
    }

    @Test
    public void anticipationBecomesDrawWhenDrawRadioButtonIsClicked() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onDrawRadioButtonClicked(0, true);
        assertThat(presenter.gameAt(0).getAnticipation(), is(Game.Anticipation.DRAW));
    }

    @Test
    public void clickMinusPositionOfRadioButtonHasNoAffect() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onAwayRadioButtonClicked(-1, true);
        // All of the anticipations are default, HOME
        for (int i = 0; i < presenter.gameSize(); i++) {
            assertThat(presenter.gameAt(i).getAnticipation(), is(Game.Anticipation.HOME));
        }
    }

    @Test
    public void clickBiggerPositionThanGameSizeOfRadioButtonHasNoAffect() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onAwayRadioButtonClicked(presenter.gameSize() + 1, true);
        // All of the anticipations are default, HOME
        for (int i = 0; i < presenter.gameSize(); i++) {
            assertThat(presenter.gameAt(i).getAnticipation(), is(Game.Anticipation.HOME));
        }
    }

    @Test
    public void WhenFabIsClickedTotoAnticipationActivityStarts() {
        Response<ResponseBody> response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text));
        presenter.onResponseTotoTop(response);
        response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text));
        presenter.onResponseTotoInfo(response);
        presenter.onFabClicked();
        assertTrue(view.isTotoAnticipationActivityStarted);
    }

    @Test
    public void startProgressBarWhenAutoAnticipationMenuIsClicked() {
        presenter.onOptionsAutoAnticipationSelected();
        assertTrue(view.isProgressing);
    }

    @Test
    public void showStartSnakeBarWhenAutoAnticipationMenuIsClicked() {
        presenter.onOptionsAutoAnticipationSelected();
        assertTrue(view.showStartSnakeBar);
    }

    @Test
    public void progressBarStopsWhenAutoAnticipationIsFinished() {
        presenter.finishAnticipation();
        assertFalse(view.isProgressing);
    }

    @Test
    public void showFinishSnakeBarWhenAutoAnticipationIsFinished() {
        presenter.finishAnticipation();
        assertTrue(view.showFinishSnakeBar);
    }

    @Test
    public void dataIsStoredWhenAutoAnticipationIsFinished() {
        presenter.finishAnticipation();
        assertNotNull(storage.totoNum());
    }

    private class MockView implements GameListView {

        private String title = "toto予想";
        private boolean isProgressing = false;
        private boolean showStartSnakeBar = false;
        private boolean showFinishSnakeBar = false;
        private boolean isTotoAnticipationActivityStarted = false;

        @Override
        public void initList() {
        }

        @Override
        public void setTitleFrom(@NonNull String xxTh, String deadline) {
            title = "第" + xxTh + "回(~" + deadline + ")";
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

        @Override
        public void goToSetting() {
        }

        @Override
        public void showAnticipationStart() {
            showStartSnakeBar = true;
        }

        @Override
        public void showAnticipationFinish() {
            showFinishSnakeBar = true;
        }

        @Override
        public void notifyDataSetChanged() {
        }

        @Override
        public void showAnticipationNotSupport() {
        }
    }

    private class MockStorage implements GameListStorage {

        private String totoNum;
        private List<Game> games = new ArrayList<>();

        @Override
        public String totoNum() {
            return totoNum;
        }

        @Override
        public Date totoDeadline() {
            return null;
        }

        @Override
        public List<Game> list(@NonNull String totoNum) {
            return games;
        }

        @Override
        public void store(@NonNull Toto toto, @NonNull List<Game> list) {
            this.totoNum = toto.number;
            games = list;
        }
    }

    private class MockSettingStorage implements SettingStorage {

        private boolean isNotify = false;

        @Override
        public boolean isDeadlineNotify() {
            return isNotify;
        }

        @Override
        public void setDeadlineNotify(boolean isEnabled) {
            isNotify = isEnabled;
        }
    }
}
