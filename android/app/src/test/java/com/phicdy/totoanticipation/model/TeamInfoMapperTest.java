package com.phicdy.totoanticipation.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamInfoMapperTest {

    @Test
    public void convertEmpty() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist(""), is(""));
    }
    @Test
    public void convertInvalidTeam() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("hoge"), is(""));
    }
    @Test
    public void convertKashima() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("鹿島"), is("鹿島アントラーズ"));
    }
    @Test
    public void convertUrawa() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("浦和"), is("浦和レッズ"));
    }
    @Test
    public void convertKofu() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("甲府"), is("ヴァンフォーレ甲府"));
    }
    @Test
    public void convertMarinosu() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("横浜Ｍ"), is("横浜F・マリノス"));
    }
    @Test
    public void convertOmiya() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("大宮"), is("大宮アルディージャ"));
    }
    @Test
    public void convertSendai() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("仙台"), is("ベガルタ仙台"));
    }
    @Test
    public void convertKobe() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("神戸"), is("ヴィッセル神戸"));
    }
    @Test
    public void convertTosu() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("鳥栖"), is("サガン鳥栖"));
    }
    @Test
    public void convertHiroshima() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("広島"), is("サンフレッチェ広島"));
    }
    @Test
    public void convertSeresso() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("Ｃ大阪"), is("セレッソ大阪"));
    }
    @Test
    public void convertGanba() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("Ｇ大阪"), is("ガンバ大阪"));
    }
    @Test
    public void convertKashiwa() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("柏"), is("柏レイソル"));
    }
    @Test
    public void convertIwata() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("磐田"), is("ジュビロ磐田"));
    }
    @Test
    public void convertFCTokyo() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("Ｆ東京"), is("FC東京"));
    }
    @Test
    public void convertKawasaki() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("川崎"), is("川崎フロンターレ"));
    }
    @Test
    public void convertNiigata() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("新潟"), is("アルビレックス新潟"));
    }
    @Test
    public void convertShimizu() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("清水"), is("清水エスパルス"));
    }
    @Test
    public void convertSapporo() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("札幌"), is("コンサドーレ札幌"));
    }
    @Test
    public void convertYamagata() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("山形"), is("モンテディオ山形"));
    }
    @Test
    public void convertMito() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("水戸"), is("水戸ホーリーホック"));
    }
    @Test
    public void convertOita() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("大分"), is("大分トリニータ"));
    }
    @Test
    public void convertMatsumoto() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("松本"), is("松本山雅FC"));
    }
    @Test
    public void convertShonan() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("湘南"), is("湘南ベルマーレ"));
    }
    @Test
    public void convertYamaguchi() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("山口"), is("レノファ山口FC"));
    }
    @Test
    public void convertTokushima() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("徳島"), is("徳島ヴォルティス"));
    }
    @Test
    public void convertFukuoka() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("福岡"), is("アビスパ福岡"));
    }
    @Test
    public void convertSanuki() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("讃岐"), is("カマタマーレ讃岐"));
    }
    @Test
    public void convertChiba() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("千葉"), is("ジェフユナイテッド千葉"));
    }
    @Test
    public void convertVerdy() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("東京Ｖ"), is("東京ヴェルディ1969"));
    }
    @Test
    public void convertKanazawa() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("金沢"), is("ツエーゲン金沢"));
    }
    @Test
    public void convertMachida() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("町田"), is("町田ゼルビア"));
    }
    @Test
    public void convertKumamoto() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("熊本"), is("ロアッソ熊本"));
    }
    @Test
    public void convertGunma() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("群馬"), is("ザスパクサツ群馬"));
    }
    @Test
    public void convertYokomaFC() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("横浜Ｃ"), is("横浜FC"));
    }
    @Test
    public void convertNagoya() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("名古屋"), is("名古屋グランパス"));
    }
    @Test
    public void convertGifu() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("岐阜"), is("FC岐阜"));
    }
    @Test
    public void convertKyoto() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("京都"), is("京都サンガF.C."));
    }
    @Test
    public void convertOkayama() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("岡山"), is("ファジアーノ岡山"));
    }
    @Test
    public void convertEhime() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("愛媛"), is("愛媛FC"));
    }
    @Test
    public void convertNagasaki() {
        assertThat(TeamInfoMapper.fullNameForFootbellGeist("長崎"), is("V・ファーレン長崎"));
    }
}
