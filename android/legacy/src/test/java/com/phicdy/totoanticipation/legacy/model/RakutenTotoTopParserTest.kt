package com.phicdy.totoanticipation.legacy.model

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.Date

class RakutenTotoTopParserTest {

    @Test
    fun secondLinkIsParsedWhenFirstLotteryDoesNotHaveToto() {
        val parser = RakutenTotoTopParser()
        assertThat(parser.latestToto(TestRakutenTotoPage.text).number, `is`("0923"))
    }

    @Test
    fun whenParseTestPageReturnCorrectDate() {
        val parser = RakutenTotoTopParser()
        // Expected is 2017/4/22 00:00:00
        val april22th2017 = 1492786800000L
        assertThat(parser.latestToto(TestRakutenTotoPage.text).deadline, `is`(Date(april22th2017)))
    }

    @Test
    fun defaultTotoReturnsWhenEmptyHtml() {
        val parser = RakutenTotoTopParser()
        assertThat(parser.latestToto("").number, `is`(Toto.DEFAULT_NUMBER))
    }

    @Test
    fun defaultTotoReturnsWhenInvalidHtml() {
        val parser = RakutenTotoTopParser()
        assertThat(parser.latestToto("<html><body>hoge</body></html>").number, `is`(Toto.DEFAULT_NUMBER))
    }
}
