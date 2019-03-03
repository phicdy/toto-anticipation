package com.phicdy.totoanticipation.model

import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class TeamInfoMapperTest {

    @Test
    fun convertEmpty() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist(""), `is`(""))
    }

    @Test
    fun convertInvalidTeam() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("hoge"), `is`(""))
    }

    @Test
    fun convertKashima() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("鹿島"), `is`("鹿島アントラーズ"))
    }

    @Test
    fun convertUrawa() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("浦和"), `is`("浦和レッズ"))
    }

    @Test
    fun convertKofu() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("甲府"), `is`("ヴァンフォーレ甲府"))
    }

    @Test
    fun convertMarinosu() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("横浜Ｍ"), `is`("横浜F・マリノス"))
    }

    @Test
    fun convertOmiya() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("大宮"), `is`("大宮アルディージャ"))
    }

    @Test
    fun convertSendai() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("仙台"), `is`("ベガルタ仙台"))
    }

    @Test
    fun convertKobe() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("神戸"), `is`("ヴィッセル神戸"))
    }

    @Test
    fun convertTosu() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("鳥栖"), `is`("サガン鳥栖"))
    }

    @Test
    fun convertHiroshima() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("広島"), `is`("サンフレッチェ広島"))
    }

    @Test
    fun convertSeresso() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("Ｃ大阪"), `is`("セレッソ大阪"))
    }

    @Test
    fun convertGanba() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("Ｇ大阪"), `is`("ガンバ大阪"))
    }

    @Test
    fun convertKashiwa() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("柏"), `is`("柏レイソル"))
    }

    @Test
    fun convertIwata() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("磐田"), `is`("ジュビロ磐田"))
    }

    @Test
    fun convertFCTokyo() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("Ｆ東京"), `is`("FC東京"))
    }

    @Test
    fun convertKawasaki() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("川崎"), `is`("川崎フロンターレ"))
    }

    @Test
    fun convertNiigata() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("新潟"), `is`("アルビレックス新潟"))
    }

    @Test
    fun convertShimizu() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("清水"), `is`("清水エスパルス"))
    }

    @Test
    fun convertSapporo() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("札幌"), `is`("コンサドーレ札幌"))
    }

    @Test
    fun convertYamagata() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("山形"), `is`("モンテディオ山形"))
    }

    @Test
    fun convertMito() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("水戸"), `is`("水戸ホーリーホック"))
    }

    @Test
    fun convertOita() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("大分"), `is`("大分トリニータ"))
    }

    @Test
    fun convertMatsumoto() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("松本"), `is`("松本山雅FC"))
    }

    @Test
    fun convertShonan() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("湘南"), `is`("湘南ベルマーレ"))
    }

    @Test
    fun convertYamaguchi() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("山口"), `is`("レノファ山口FC"))
    }

    @Test
    fun convertTokushima() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("徳島"), `is`("徳島ヴォルティス"))
    }

    @Test
    fun convertFukuoka() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("福岡"), `is`("アビスパ福岡"))
    }

    @Test
    fun convertSanuki() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("讃岐"), `is`("カマタマーレ讃岐"))
    }

    @Test
    fun convertChiba() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("千葉"), `is`("ジェフユナイテッド千葉"))
    }

    @Test
    fun convertVerdy() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("東京Ｖ"), `is`("東京ヴェルディ1969"))
    }

    @Test
    fun convertKanazawa() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("金沢"), `is`("ツエーゲン金沢"))
    }

    @Test
    fun convertMachida() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("町田"), `is`("町田ゼルビア"))
    }

    @Test
    fun convertKumamoto() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("熊本"), `is`("ロアッソ熊本"))
    }

    @Test
    fun convertGunma() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("群馬"), `is`("ザスパクサツ群馬"))
    }

    @Test
    fun convertYokomaFC() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("横浜Ｃ"), `is`("横浜FC"))
    }

    @Test
    fun convertNagoya() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("名古屋"), `is`("名古屋グランパス"))
    }

    @Test
    fun convertGifu() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("岐阜"), `is`("FC岐阜"))
    }

    @Test
    fun convertKyoto() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("京都"), `is`("京都サンガF.C."))
    }

    @Test
    fun convertOkayama() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("岡山"), `is`("ファジアーノ岡山"))
    }

    @Test
    fun convertEhime() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("愛媛"), `is`("愛媛FC"))
    }

    @Test
    fun convertNagasaki() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("長崎"), `is`("V・ファーレン長崎"))
    }

    @Test
    fun convertIwate() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("岩手"), `is`("グルージャ盛岡"))
    }

    @Test
    fun convertHachinohe() {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist("八戸"), `is`("ヴァンラーレ八戸"))
    }
}
