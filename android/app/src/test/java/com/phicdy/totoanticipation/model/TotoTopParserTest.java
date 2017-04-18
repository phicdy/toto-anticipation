package com.phicdy.totoanticipation.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TotoTopParserTest {

    @Test
    public void SecondLinkIsParsedWhenFirstLotteryDoesNotHaveToto() {
        TotoTopParser parser = new TotoTopParser();
        assertThat(parser.latestLotteryLink(SecondLotteryLatestTotoTopPage.text),
                is("http://www.toto-dream.com/dci/I/IPA/IPA01.do?op=disptotoLotInfo&holdCntId=0923"));
    }

    @Test
    public void EmptyLinkReturnsWhenEmptyHtml() {
        TotoTopParser parser = new TotoTopParser();
        assertThat(parser.latestLotteryLink(""), is(""));
    }

    @Test
    public void EmptyLinkReturnsWhenInvalidHtml() {
        TotoTopParser parser = new TotoTopParser();
        assertThat(parser.latestLotteryLink("<html><body>hoge</body></html>"), is(""));
    }
}
