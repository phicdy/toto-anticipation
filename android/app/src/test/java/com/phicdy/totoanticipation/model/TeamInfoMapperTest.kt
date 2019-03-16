package com.phicdy.totoanticipation.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class TeamInfoMapperTest {

    companion object {
        @DataPoints
        @JvmField
        var gameAndResult = listOf(
                Pair("", ""),
                Pair("hoge", ""),
                Pair("鹿島", "鹿島アントラーズ"),
                Pair("浦和", "浦和レッズ"),
                Pair("甲府", "ヴァンフォーレ甲府"),
                Pair("横浜Ｍ", "横浜F・マリノス"),
                Pair("大宮", "大宮アルディージャ"),
                Pair("仙台", "ベガルタ仙台"),
                Pair("神戸", "ヴィッセル神戸"),
                Pair("鳥栖", "サガン鳥栖"),
                Pair("広島", "サンフレッチェ広島"),
                Pair("Ｃ大阪", "セレッソ大阪"),
                Pair("Ｇ大阪", "ガンバ大阪"),
                Pair("柏", "柏レイソル"),
                Pair("磐田", "ジュビロ磐田"),
                Pair("Ｆ東京", "FC東京"),
                Pair("川崎", "川崎フロンターレ"),
                Pair("新潟", "アルビレックス新潟"),
                Pair("清水", "清水エスパルス"),
                Pair("札幌", "コンサドーレ札幌"),
                Pair("山形", "モンテディオ山形"),
                Pair("水戸", "水戸ホーリーホック"),
                Pair("大分", "大分トリニータ"),
                Pair("松本", "松本山雅FC"),
                Pair("湘南", "湘南ベルマーレ"),
                Pair("山口", "レノファ山口FC"),
                Pair("徳島", "徳島ヴォルティス"),
                Pair("福岡", "アビスパ福岡"),
                Pair("讃岐", "カマタマーレ讃岐"),
                Pair("千葉", "ジェフユナイテッド千葉"),
                Pair("東京Ｖ", "東京ヴェルディ1969"),
                Pair("金沢", "ツエーゲン金沢"),
                Pair("町田", "町田ゼルビア"),
                Pair("熊本", "ロアッソ熊本"),
                Pair("群馬", "ザスパクサツ群馬"),
                Pair("横浜Ｃ", "横浜FC"),
                Pair("名古屋", "名古屋グランパス"),
                Pair("岐阜", "FC岐阜"),
                Pair("京都", "京都サンガF.C."),
                Pair("岡山", "ファジアーノ岡山"),
                Pair("愛媛", "愛媛FC"),
                Pair("長崎", "V・ファーレン長崎"),
                Pair("岩手", "グルージャ盛岡"),
                Pair("八戸", "ヴァンラーレ八戸"),
                Pair("琉球", "FC琉球"),
                Pair("栃木", "栃木SC"),
                Pair("鹿児島", "鹿児島ユナイテッドFC")
        )
    }

    @Theory
    fun `when map team then return the team in FootballGeist`(mapping: Pair<String, String>) {
        assertThat(TeamInfoMapper().fullNameForFootbellGeist(mapping.first), `is`(mapping.second))
    }

}
