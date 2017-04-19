package com.phicdy.totoanticipation.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RakutenTotoTopParserTest {

    @Test
    public void SecondLinkIsParsedWhenFirstLotteryDoesNotHaveToto() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        assertThat(parser.latestTotoNumber(TestRakutenTotoPage.text), is("0923"));
    }

    @Test
    public void EmptyNumberReturnsWhenEmptyHtml() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        assertThat(parser.latestTotoNumber(""), is(""));
    }

    @Test
    public void EmptyNumberReturnsWhenInvalidHtml() {
        RakutenTotoTopParser parser = new RakutenTotoTopParser();
        assertThat(parser.latestTotoNumber("<html><body>hoge</body></html>"), is(""));
    }
}
