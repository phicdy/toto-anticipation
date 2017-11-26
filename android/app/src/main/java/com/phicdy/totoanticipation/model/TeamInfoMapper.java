package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

public class TeamInfoMapper {

    /**
     * Return full name of the team.
     *
     * @param shrunkName Shrunk team name in Rakuten toto
     * @return Full name of the team in J League ranking
     */
    public static String fullNameForJLeagueRanking(@NonNull String shrunkName) {
        if (shrunkName.equals("Ｆ東京")) return "ＦＣ東京";
        if (shrunkName.equals("横浜Ｍ")) return "横浜Ｆ・マリノス";
        if (shrunkName.equals("札幌")) return "北海道コンサドーレ札幌";
        if (shrunkName.equals("東京Ｖ")) return "東京ヴェルディ";
        if (shrunkName.equals("岐阜")) return "ＦＣ岐阜";
        if (shrunkName.equals("横浜Ｃ")) return "横浜ＦＣ";
        if (shrunkName.equals("町田")) return "ＦＣ町田ゼルビア";
        if (shrunkName.equals("長崎")) return "Ｖ・ファーレン長崎";
        if (shrunkName.equals("松本")) return "松本山雅ＦＣ";
        if (shrunkName.equals("愛媛")) return "愛媛ＦＣ";
        if (shrunkName.equals("京都")) return "京都サンガF.C.";
        if (shrunkName.equals("山口")) return "レノファ山口ＦＣ";
        if (shrunkName.equals("Ｙ横浜")) return "Ｙ．Ｓ．Ｃ．Ｃ．横浜";
        if (shrunkName.equals("栃木")) return "栃木ＳＣ";
        if (shrunkName.equals("琉球")) return "ＦＣ琉球";
        if (shrunkName.equals("長野")) return "ＡＣ長野パルセイロ";
        if (shrunkName.equals("藤枝")) return "藤枝ＭＹＦＣ";
        if (shrunkName.equals("相模原")) return "ＳＣ相模原";
        return fullNameForFootbellGeist(shrunkName);
    }

    /**
     * Return full name of the team.
     *
     * @param shrunkName Shrunk team name in Rakuten toto
     * @return Full name of the team
     */
    public static String fullNameForFootbellGeist(@NonNull String shrunkName) {
        if (shrunkName.equals("鹿島")) return "鹿島アントラーズ";
        if (shrunkName.equals("浦和")) return "浦和レッズ";
        if (shrunkName.equals("甲府")) return "ヴァンフォーレ甲府";
        if (shrunkName.equals("横浜Ｍ")) return "横浜F・マリノス";
        if (shrunkName.equals("大宮")) return "大宮アルディージャ";
        if (shrunkName.equals("仙台")) return "ベガルタ仙台";
        if (shrunkName.equals("神戸")) return "ヴィッセル神戸";
        if (shrunkName.equals("鳥栖")) return "サガン鳥栖";
        if (shrunkName.equals("広島")) return "サンフレッチェ広島";
        if (shrunkName.equals("Ｃ大阪")) return "セレッソ大阪";
        if (shrunkName.equals("Ｇ大阪")) return "ガンバ大阪";
        if (shrunkName.equals("柏")) return "柏レイソル";
        if (shrunkName.equals("磐田")) return "ジュビロ磐田";
        if (shrunkName.equals("Ｆ東京")) return "FC東京";
        if (shrunkName.equals("川崎")) return "川崎フロンターレ";
        if (shrunkName.equals("新潟")) return "アルビレックス新潟";
        if (shrunkName.equals("清水")) return "清水エスパルス";
        if (shrunkName.equals("札幌")) return "コンサドーレ札幌";
        if (shrunkName.equals("山形")) return "モンテディオ山形";
        if (shrunkName.equals("水戸")) return "水戸ホーリーホック";
        if (shrunkName.equals("大分")) return "大分トリニータ";
        if (shrunkName.equals("松本")) return "松本山雅FC";
        if (shrunkName.equals("湘南")) return "湘南ベルマーレ";
        if (shrunkName.equals("山口")) return "レノファ山口FC";
        if (shrunkName.equals("徳島")) return "徳島ヴォルティス";
        if (shrunkName.equals("福岡")) return "アビスパ福岡";
        if (shrunkName.equals("讃岐")) return "カマタマーレ讃岐";
        if (shrunkName.equals("千葉")) return "ジェフユナイテッド千葉";
        if (shrunkName.equals("東京Ｖ")) return "東京ヴェルディ1969";
        if (shrunkName.equals("金沢")) return "ツエーゲン金沢";
        if (shrunkName.equals("町田")) return "町田ゼルビア";
        if (shrunkName.equals("熊本")) return "ロアッソ熊本";
        if (shrunkName.equals("群馬")) return "ザスパクサツ群馬";
        if (shrunkName.equals("横浜Ｃ")) return "横浜FC";
        if (shrunkName.equals("名古屋")) return "名古屋グランパス";
        if (shrunkName.equals("岐阜")) return "FC岐阜";
        if (shrunkName.equals("京都")) return "京都サンガF.C.";
        if (shrunkName.equals("岡山")) return "ファジアーノ岡山";
        if (shrunkName.equals("愛媛")) return "愛媛FC";
        if (shrunkName.equals("長崎")) return "V・ファーレン長崎";
        if (shrunkName.equals("沼津")) return "アスルクラロ沼津";
        if (shrunkName.equals("栃木")) return "栃木SC";
        if (shrunkName.equals("琉球")) return "FC琉球";
        if (shrunkName.equals("富山")) return "カターレ富山";
        if (shrunkName.equals("長野")) return "AC長野パルセイロ";
        if (shrunkName.equals("北九州")) return "ギラヴァンツ北九州";
        if (shrunkName.equals("鳥取")) return "ガイナーレ鳥取";
        if (shrunkName.equals("秋田")) return "ブラウブリッツ秋田";
        if (shrunkName.equals("福島")) return "福島ユナイテッドFC";
        if (shrunkName.equals("Ｙ横浜")) return "Y.S.C.C.";
        if (shrunkName.equals("藤枝")) return "藤枝MYFC";
        if (shrunkName.equals("相模原")) return "SC相模原";
        if (shrunkName.equals("盛岡")) return "グルージャ盛岡";
        return "";
    }

    /**
     * Return yahoo news URL of the team
     * @param shrunkName Shrunk team name
     * @return Yahoo news URL of the team
     */
    public static String yahooNewsUrl(@NonNull String shrunkName) {
        if (shrunkName.equals("鹿島")) return "https://follow.yahoo.co.jp/themes/010704251227ca5d1597";
        if (shrunkName.equals("浦和")) return "https://follow.yahoo.co.jp/themes/04316859141f07751597";
        if (shrunkName.equals("甲府")) return "https://follow.yahoo.co.jp/themes/01515540fe35ebc44478";
        if (shrunkName.equals("横浜Ｍ")) return "https://follow.yahoo.co.jp/themes/06425d9b390b2a761597";
        if (shrunkName.equals("大宮")) return "https://follow.yahoo.co.jp/themes/0e97b6f0c765b7b51626";
        if (shrunkName.equals("仙台")) return "https://follow.yahoo.co.jp/themes/0e497a9153552bb91626";
        if (shrunkName.equals("神戸")) return "https://follow.yahoo.co.jp/themes/0b4ca90ec766caed1627";
        if (shrunkName.equals("鳥栖")) return "https://follow.yahoo.co.jp/themes/003188a22de36d6e1627";
        if (shrunkName.equals("広島")) return "https://follow.yahoo.co.jp/themes/0898547ed1acc5f62974";
        if (shrunkName.equals("Ｃ大阪")) return "https://follow.yahoo.co.jp/themes/054292a8521db8751618";
        if (shrunkName.equals("Ｇ大阪")) return "https://follow.yahoo.co.jp/themes/0a18482afd8c2a4f2911";
        if (shrunkName.equals("柏")) return "https://follow.yahoo.co.jp/themes/01fed3fdd8a465f81626";
        if (shrunkName.equals("磐田")) return "https://follow.yahoo.co.jp/themes/00adc1f4f92978161627";
        if (shrunkName.equals("Ｆ東京")) return "https://follow.yahoo.co.jp/themes/0f190139089919e91597";
        if (shrunkName.equals("川崎")) return "https://follow.yahoo.co.jp/themes/0e7f53af851d48bd1627";
        if (shrunkName.equals("新潟")) return "https://follow.yahoo.co.jp/themes/0eac18c5558f5f8f1627";
        if (shrunkName.equals("清水")) return "https://follow.yahoo.co.jp/themes/00ac7122776ad94b1618";
        if (shrunkName.equals("札幌")) return "https://follow.yahoo.co.jp/themes/0d1436d9f9361ae01589";
        if (shrunkName.equals("山形")) return "https://follow.yahoo.co.jp/themes/036ddeff0b6c85693331";
        if (shrunkName.equals("水戸")) return "https://follow.yahoo.co.jp/themes/0cf94174c1a3baf43366";
        if (shrunkName.equals("大分")) return "https://follow.yahoo.co.jp/themes/07ea89c49d07a4642861";
        if (shrunkName.equals("松本")) return "https://follow.yahoo.co.jp/themes/00630ba3d643e7e14523";
        if (shrunkName.equals("湘南")) return "https://follow.yahoo.co.jp/themes/08879afd64168ce03969";
        if (shrunkName.equals("山口")) return "https://follow.yahoo.co.jp/themes/046f711e5e59a7642237";
        if (shrunkName.equals("徳島")) return "https://follow.yahoo.co.jp/themes/09a8831df6b875814737";
        if (shrunkName.equals("福岡")) return "https://follow.yahoo.co.jp/themes/0349f9a6a9d4b42f4855";
        if (shrunkName.equals("讃岐")) return "https://follow.yahoo.co.jp/themes/02ec642844c09efa5650";
        if (shrunkName.equals("千葉")) return "https://follow.yahoo.co.jp/themes/038aae07d6070bad3702";
        if (shrunkName.equals("東京Ｖ")) return "https://follow.yahoo.co.jp/themes/000ae6f45e2481d63828";
        if (shrunkName.equals("金沢")) return "https://follow.yahoo.co.jp/themes/09582bedeaa6d0597615";
        if (shrunkName.equals("町田")) return "https://follow.yahoo.co.jp/themes/0c1a8a697a8cd53b3612";
        if (shrunkName.equals("熊本")) return "https://follow.yahoo.co.jp/themes/031dc9c6d60f1e554982";
        if (shrunkName.equals("群馬")) return "https://follow.yahoo.co.jp/themes/0e3c1cd4c068ca553533";
        if (shrunkName.equals("横浜Ｃ")) return "https://follow.yahoo.co.jp/themes/005fe68860d0a7cf3923";
        if (shrunkName.equals("名古屋")) return "https://follow.yahoo.co.jp/themes/010984813b836cc93328";
        if (shrunkName.equals("岐阜")) return "https://follow.yahoo.co.jp/themes/039aaf5b315723f54596";
        if (shrunkName.equals("京都")) return "https://follow.yahoo.co.jp/themes/052369d0cd6803123229";
        if (shrunkName.equals("岡山")) return "https://follow.yahoo.co.jp/themes/0d21e1411840d7d94695";
        if (shrunkName.equals("愛媛")) return "https://follow.yahoo.co.jp/themes/0beb2a057bde3e784809";
        if (shrunkName.equals("長崎")) return "https://follow.yahoo.co.jp/themes/0e43011e7a4f342b4949";
        if (shrunkName.equals("沼津")) return "https://follow.yahoo.co.jp/themes/05b6151e057690a34447";
        if (shrunkName.equals("栃木")) return "https://follow.yahoo.co.jp/themes/0f63ab146bb622654383";
        if (shrunkName.equals("琉球")) return "https://follow.yahoo.co.jp/themes/074e45adaffa8bc39675";
        if (shrunkName.equals("富山")) return "https://follow.yahoo.co.jp/themes/09a08261f6883fd64550";
        if (shrunkName.equals("長野")) return "https://follow.yahoo.co.jp/themes/04ae0c1cd0c54bd87700";
        if (shrunkName.equals("北九州")) return "https://follow.yahoo.co.jp/themes/0d49472ef6b17a314886";
        if (shrunkName.equals("鳥取")) return "https://follow.yahoo.co.jp/themes/06594dbf586c2c014648";
        if (shrunkName.equals("秋田")) return "https://follow.yahoo.co.jp/themes/08277fafba455a217434";
        if (shrunkName.equals("福島")) return "https://follow.yahoo.co.jp/themes/04d650e749513b483612";
        if (shrunkName.equals("Ｙ横浜")) return "https://follow.yahoo.co.jp/themes/05ec2038c45124950041";
        if (shrunkName.equals("藤枝")) return "https://follow.yahoo.co.jp/themes/0c2549eb94ecf5ce0452";
        if (shrunkName.equals("相模原")) return "https://follow.yahoo.co.jp/themes/0360a0cc617c9c4e0298";
        if (shrunkName.equals("鹿児島")) return "https://follow.yahoo.co.jp/themes/024476e4fd08963a6430";
        if (shrunkName.equals("盛岡")) return "https://follow.yahoo.co.jp/themes/0279ed51de2c87282435";
        return "";
    }
}
