package com.phicdy.totoanticipation.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamInfoMapperTest {

    @Test
    public void convertEmpty() {
        assertThat(TeamInfoMapper.fullName(""), is(""));
    }
    @Test
    public void convertInvalidTeam() {
        assertThat(TeamInfoMapper.fullName("hoge"), is(""));
    }
    @Test
    public void convertKashima() {
        assertThat(TeamInfoMapper.fullName("鹿島"), is("鹿島アントラーズ"));
    }
    @Test
    public void convertUrawa() {
        assertThat(TeamInfoMapper.fullName("浦和"), is("浦和レッズ"));
    }
    @Test
    public void convertKofu() {
        assertThat(TeamInfoMapper.fullName("甲府"), is("ヴァンフォーレ甲府"));
    }
    @Test
    public void convertMarinosu() {
        assertThat(TeamInfoMapper.fullName("横浜Ｍ"), is("横浜F・マリノス"));
    }
    @Test
    public void convertOmiya() {
        assertThat(TeamInfoMapper.fullName("大宮"), is("大宮アルディージャ"));
    }
    @Test
    public void convertSendai() {
        assertThat(TeamInfoMapper.fullName("仙台"), is("ベガルタ仙台"));
    }
    @Test
    public void convertKobe() {
        assertThat(TeamInfoMapper.fullName("神戸"), is("ヴィッセル神戸"));
    }
    @Test
    public void convertTosu() {
        assertThat(TeamInfoMapper.fullName("鳥栖"), is("サガン鳥栖"));
    }
    @Test
    public void convertHiroshima() {
        assertThat(TeamInfoMapper.fullName("広島"), is("サンフレッチェ広島"));
    }
    @Test
    public void convertSeresso() {
        assertThat(TeamInfoMapper.fullName("Ｃ大阪"), is("セレッソ大阪"));
    }
    @Test
    public void convertGanba() {
        assertThat(TeamInfoMapper.fullName("Ｇ大阪"), is("ガンバ大阪"));
    }
    @Test
    public void convertKashiwa() {
        assertThat(TeamInfoMapper.fullName("柏"), is("柏レイソル"));
    }
    @Test
    public void convertIwata() {
        assertThat(TeamInfoMapper.fullName("磐田"), is("ジュビロ磐田"));
    }
    @Test
    public void convertFCTokyo() {
        assertThat(TeamInfoMapper.fullName("Ｆ東京"), is("FC東京"));
    }
    @Test
    public void convertKawasaki() {
        assertThat(TeamInfoMapper.fullName("川崎"), is("川崎フロンターレ"));
    }
    @Test
    public void convertNiigata() {
        assertThat(TeamInfoMapper.fullName("新潟"), is("アルビレックス新潟"));
    }
    @Test
    public void convertShimizu() {
        assertThat(TeamInfoMapper.fullName("清水"), is("清水エスパルス"));
    }
    @Test
    public void convertSapporo() {
        assertThat(TeamInfoMapper.fullName("札幌"), is("コンサドーレ札幌"));
    }
    @Test
    public void convertYamagata() {
        assertThat(TeamInfoMapper.fullName("山形"), is("モンテディオ山形"));
    }
    @Test
    public void convertMito() {
        assertThat(TeamInfoMapper.fullName("水戸"), is("水戸ホーリーホック"));
    }
    @Test
    public void convertOita() {
        assertThat(TeamInfoMapper.fullName("大分"), is("大分トリニータ"));
    }
    @Test
    public void convertMatsumoto() {
        assertThat(TeamInfoMapper.fullName("松本"), is("松本山雅FC"));
    }
    @Test
    public void convertShonan() {
        assertThat(TeamInfoMapper.fullName("湘南"), is("湘南ベルマーレ"));
    }
    @Test
    public void convertYamaguchi() {
        assertThat(TeamInfoMapper.fullName("山口"), is("レノファ山口FC"));
    }
    @Test
    public void convertTokushima() {
        assertThat(TeamInfoMapper.fullName("徳島"), is("徳島ヴォルティス"));
    }
    @Test
    public void convertFukuoka() {
        assertThat(TeamInfoMapper.fullName("福岡"), is("アビスパ福岡"));
    }
    @Test
    public void convertSanuki() {
        assertThat(TeamInfoMapper.fullName("讃岐"), is("カマタマーレ讃岐"));
    }
    @Test
    public void convertChiba() {
        assertThat(TeamInfoMapper.fullName("千葉"), is("ジェフユナイテッド千葉"));
    }
    @Test
    public void convertVerdy() {
        assertThat(TeamInfoMapper.fullName("東京Ｖ"), is("東京ヴェルディ1969"));
    }
    @Test
    public void convertKanazawa() {
        assertThat(TeamInfoMapper.fullName("金沢"), is("ツエーゲン金沢"));
    }
    @Test
    public void convertMachida() {
        assertThat(TeamInfoMapper.fullName("町田"), is("町田ゼルビア"));
    }
    @Test
    public void convertKumamoto() {
        assertThat(TeamInfoMapper.fullName("熊本"), is("ロアッソ熊本"));
    }
    @Test
    public void convertGunma() {
        assertThat(TeamInfoMapper.fullName("群馬"), is("ザスパクサツ群馬"));
    }
    @Test
    public void convertYokomaFC() {
        assertThat(TeamInfoMapper.fullName("横浜Ｃ"), is("横浜FC"));
    }
    @Test
    public void convertNagoya() {
        assertThat(TeamInfoMapper.fullName("名古屋"), is("名古屋グランパス"));
    }
    @Test
    public void convertGifu() {
        assertThat(TeamInfoMapper.fullName("岐阜"), is("FC岐阜"));
    }
    @Test
    public void convertKyoto() {
        assertThat(TeamInfoMapper.fullName("京都"), is("京都サンガF.C."));
    }
    @Test
    public void convertOkayama() {
        assertThat(TeamInfoMapper.fullName("岡山"), is("ファジアーノ岡山"));
    }
    @Test
    public void convertEhime() {
        assertThat(TeamInfoMapper.fullName("愛媛"), is("愛媛FC"));
    }
    @Test
    public void convertNagasaki() {
        assertThat(TeamInfoMapper.fullName("長崎"), is("V・ファーレン長崎"));
    }
}
