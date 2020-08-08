package com.phicdy.totoanticipation.legacy.presenter

import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.legacy.view.TotoAnticipationView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TotoAnticipationPresenterTest {
    val presenter = TotoAnticipationPresenter("0973")

    @Before
    fun setup() {
        presenter.view = Mockito.mock(TotoAnticipationView::class.java)
    }

    @Test
    fun initListenerIsCalledWhenOnCreate() {
        presenter.onCreate()
        Mockito.verify(presenter.view, Mockito.times(1)).initListener()
    }

    @Test
    fun totoTopPageIsloadedWhenOnResume() {
        presenter.onCreate()
        presenter.onResume()
        Mockito.verify(presenter.view, Mockito.times(1))
                .loadUrl("https://sp.toto-dream.com/dcs/subos/screen/si01/ssin026/PGSSIN02601InittotoSP.form?holdCntId=0973")
    }

    @Test
    fun clickBuyNowLinkScriptIsExecutedWhenTotoTopPageFinishedToLoad() {
        val script = "var aTags = document.getElementsByTagName('a');\n" +
                "var searchText = '今すぐ購入する';\n" +
                "for (var i = 0; i < aTags.length; i++) {\n" +
                "  if (aTags[i].textContent == searchText) {\n" +
                "    aTags[i].click();\n" +
                "    break;\n" +
                "  }\n" +
                "}"
        presenter.onCreate()
        presenter.onResume()
        val games = mutableListOf<Game>()
        presenter.onPageFinished("https://sp.toto-dream.com/dcs/subos/screen/si01/ssin026/PGSSIN02601InittotoSP.form?holdCntId=0973", games)
        Mockito.verify(presenter.view, Mockito.times(1))
                .exec(script)
    }

    @Test
    fun clickFirstHomeCheckboxScriptIsExecutedWhenOtherPageIsLoaded() {
        val script = "var nodeList1 = document.getElementsByName('chkbox_1_0');" +
                "nodeList1[0].checked = true;"
        presenter.onCreate()
        presenter.onResume()
        val games = mutableListOf<Game>()
        // chkbox_1_0
        val game1 = Game("home", "away")
        game1.anticipation = Game.Anticipation.HOME
        // chkbox_2_1
        val game2 = Game("home", "away")
        game2.anticipation = Game.Anticipation.DRAW
        // chkbox_3_2
        val game3 = Game("home", "away")
        game3.anticipation = Game.Anticipation.AWAY
        games.add(game1)
        games.add(game2)
        games.add(game3)
        presenter.onPageFinished("http://sp.toto-dream.com/hogehoge", games)
        Mockito.verify(presenter.view, Mockito.times(1))
                .exec(script)
    }

    @Test
    fun clickSecondDrawCheckboxScriptIsExecutedWhenOtherPageIsLoaded() {
        val script = "var nodeList2 = document.getElementsByName('chkbox_2_1');" +
                "nodeList2[0].checked = true;"
        presenter.onCreate()
        presenter.onResume()
        val games = mutableListOf<Game>()
        // chkbox_1_0
        val game1 = Game("home", "away")
        game1.anticipation = Game.Anticipation.HOME
        // chkbox_2_1
        val game2 = Game("home", "away")
        game2.anticipation = Game.Anticipation.DRAW
        // chkbox_3_2
        val game3 = Game("home", "away")
        game3.anticipation = Game.Anticipation.AWAY
        games.add(game1)
        games.add(game2)
        games.add(game3)
        presenter.onPageFinished("http://sp.toto-dream.com/hogehoge", games)
        Mockito.verify(presenter.view, Mockito.times(1))
                .exec(script)
    }

    @Test
    fun clickThirdAwayCheckboxScriptIsExecutedWhenOtherPageIsLoaded() {
        val script = "var nodeList3 = document.getElementsByName('chkbox_3_2');" +
                "nodeList3[0].checked = true;"
        presenter.onCreate()
        presenter.onResume()
        val games = mutableListOf<Game>()
        // chkbox_1_0
        val game1 = Game("home", "away")
        game1.anticipation = Game.Anticipation.HOME
        // chkbox_2_1
        val game2 = Game("home", "away")
        game2.anticipation = Game.Anticipation.DRAW
        // chkbox_3_2
        val game3 = Game("home", "away")
        game3.anticipation = Game.Anticipation.AWAY
        games.add(game1)
        games.add(game2)
        games.add(game3)
        presenter.onPageFinished("http://sp.toto-dream.com/hogehoge", games)
        Mockito.verify(presenter.view, Mockito.times(1))
                .exec(script)
    }
}