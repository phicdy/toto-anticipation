package com.phicdy.totoanticipation.model;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RakutenTotoTopParserTest {

    @Test
    public void SecondLinkIsParsedWhenFirstLotteryDoesNotHaveToto() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        assertThat(parser.latestToto(TestRakutenTotoPage.text).number, is("0923"));
    }

    @Test
    public void WhenParseTestPageReturnCorrectDate() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        // Expected is 2017/4/22 00:00:00
        long april22th2017 = 1492786800000L;
        assertThat(parser.latestToto(TestRakutenTotoPage.text).deadline, is(new Date(april22th2017)));
    }

    @Test
    public void DefaultTotoReturnsWhenEmptyHtml() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        assertThat(parser.latestToto("").number, is(Toto.DEFAULT_NUMBER));
    }

    @Test
    public void DefaultTotoReturnsWhenInvalidHtml() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        assertThat(parser.latestToto("<html><body>hoge</body></html>").number, is(Toto.DEFAULT_NUMBER));
    }
}
