package com.phicdy.totoanticipation.model

class TeamInfoMapper {

    /**
     * Return full name of the team.
     *
     * @param shrunkName Shrunk team name in Rakuten toto
     * @return Full name of the team in J League ranking
     */
    fun fullNameForJLeagueRanking(shrunkName: String) = when (shrunkName) {
        "Ｆ東京" -> "ＦＣ東京"
        "横浜Ｍ" -> "横浜Ｆ・マリノス"
        "札幌" -> "北海道コンサドーレ札幌"
        "東京Ｖ" -> "東京ヴェルディ"
        "岐阜" -> "ＦＣ岐阜"
        "横浜Ｃ" -> "横浜ＦＣ"
        "町田" -> "ＦＣ町田ゼルビア"
        "長崎" -> "Ｖ・ファーレン長崎"
        "松本" -> "松本山雅ＦＣ"
        "愛媛" -> "愛媛ＦＣ"
        "京都" -> "京都サンガF.C."
        "山口" -> "レノファ山口ＦＣ"
        "Ｙ横浜" -> "Ｙ．Ｓ．Ｃ．Ｃ．横浜"
        "栃木" -> "栃木ＳＣ"
        "琉球" -> "ＦＣ琉球"
        "長野" -> "ＡＣ長野パルセイロ"
        "藤枝" -> "藤枝ＭＹＦＣ"
        "相模原" -> "ＳＣ相模原"
        else -> fullNameForFootbellGeist(shrunkName)
    }

    /**
     * Return full name of the team.
     *
     * @param shrunkName Shrunk team name in Rakuten toto
     * @return Full name of the team
     */
    fun fullNameForFootbellGeist(shrunkName: String) = when (shrunkName) {
        "鹿島" -> "鹿島アントラーズ"
        "浦和" -> "浦和レッズ"
        "甲府" -> "ヴァンフォーレ甲府"
        "横浜Ｍ" -> "横浜F・マリノス"
        "大宮" -> "大宮アルディージャ"
        "仙台" -> "ベガルタ仙台"
        "神戸" -> "ヴィッセル神戸"
        "鳥栖" -> "サガン鳥栖"
        "広島" -> "サンフレッチェ広島"
        "Ｃ大阪" -> "セレッソ大阪"
        "Ｇ大阪" -> "ガンバ大阪"
        "柏" -> "柏レイソル"
        "磐田" -> "ジュビロ磐田"
        "Ｆ東京" -> "FC東京"
        "川崎" -> "川崎フロンターレ"
        "新潟" -> "アルビレックス新潟"
        "清水" -> "清水エスパルス"
        "札幌" -> "コンサドーレ札幌"
        "山形" -> "モンテディオ山形"
        "水戸" -> "水戸ホーリーホック"
        "大分" -> "大分トリニータ"
        "松本" -> "松本山雅FC"
        "湘南" -> "湘南ベルマーレ"
        "山口" -> "レノファ山口FC"
        "徳島" -> "徳島ヴォルティス"
        "福岡" -> "アビスパ福岡"
        "讃岐" -> "カマタマーレ讃岐"
        "千葉" -> "ジェフユナイテッド千葉"
        "東京Ｖ" -> "東京ヴェルディ1969"
        "金沢" -> "ツエーゲン金沢"
        "町田" -> "町田ゼルビア"
        "熊本" -> "ロアッソ熊本"
        "群馬" -> "ザスパクサツ群馬"
        "横浜Ｃ" -> "横浜FC"
        "名古屋" -> "名古屋グランパス"
        "岐阜" -> "FC岐阜"
        "京都" -> "京都サンガF.C."
        "岡山" -> "ファジアーノ岡山"
        "愛媛" -> "愛媛FC"
        "長崎" -> "V・ファーレン長崎"
        "沼津" -> "アスルクラロ沼津"
        "栃木" -> "栃木SC"
        "琉球" -> "FC琉球"
        "富山" -> "カターレ富山"
        "長野" -> "AC長野パルセイロ"
        "北九州" -> "ギラヴァンツ北九州"
        "鳥取" -> "ガイナーレ鳥取"
        "秋田" -> "ブラウブリッツ秋田"
        "福島" -> "福島ユナイテッドFC"
        "Ｙ横浜" -> "Y.S.C.C."
        "藤枝" -> "藤枝MYFC"
        "相模原" -> "SC相模原"
        "盛岡" -> "グルージャ盛岡"
        else -> ""
    }

    /**
     * Return yahoo news URL of the team
     * @param shrunkName Shrunk team name
     * @return Yahoo news URL of the team
     */
    fun yahooNewsUrl(shrunkName: String) = when (shrunkName) {
        "鹿島" -> "https://follow.yahoo.co.jp/themes/010704251227ca5d1597"
        "浦和" -> "https://follow.yahoo.co.jp/themes/04316859141f07751597"
        "甲府" -> "https://follow.yahoo.co.jp/themes/01515540fe35ebc44478"
        "横浜Ｍ" -> "https://follow.yahoo.co.jp/themes/06425d9b390b2a761597"
        "大宮" -> "https://follow.yahoo.co.jp/themes/0e97b6f0c765b7b51626"
        "仙台" -> "https://follow.yahoo.co.jp/themes/0e497a9153552bb91626"
        "神戸" -> "https://follow.yahoo.co.jp/themes/0b4ca90ec766caed1627"
        "鳥栖" -> "https://follow.yahoo.co.jp/themes/003188a22de36d6e1627"
        "広島" -> "https://follow.yahoo.co.jp/themes/0898547ed1acc5f62974"
        "Ｃ大阪" -> "https://follow.yahoo.co.jp/themes/054292a8521db8751618"
        "Ｇ大阪" -> "https://follow.yahoo.co.jp/themes/0a18482afd8c2a4f2911"
        "柏" -> "https://follow.yahoo.co.jp/themes/01fed3fdd8a465f81626"
        "磐田" -> "https://follow.yahoo.co.jp/themes/00adc1f4f92978161627"
        "Ｆ東京" -> "https://follow.yahoo.co.jp/themes/0f190139089919e91597"
        "川崎" -> "https://follow.yahoo.co.jp/themes/0e7f53af851d48bd1627"
        "新潟" -> "https://follow.yahoo.co.jp/themes/0eac18c5558f5f8f1627"
        "清水" -> "https://follow.yahoo.co.jp/themes/00ac7122776ad94b1618"
        "札幌" -> "https://follow.yahoo.co.jp/themes/0d1436d9f9361ae01589"
        "山形" -> "https://follow.yahoo.co.jp/themes/036ddeff0b6c85693331"
        "水戸" -> "https://follow.yahoo.co.jp/themes/0cf94174c1a3baf43366"
        "大分" -> "https://follow.yahoo.co.jp/themes/07ea89c49d07a4642861"
        "松本" -> "https://follow.yahoo.co.jp/themes/00630ba3d643e7e14523"
        "湘南" -> "https://follow.yahoo.co.jp/themes/08879afd64168ce03969"
        "山口" -> "https://follow.yahoo.co.jp/themes/046f711e5e59a7642237"
        "徳島" -> "https://follow.yahoo.co.jp/themes/09a8831df6b875814737"
        "福岡" -> "https://follow.yahoo.co.jp/themes/0349f9a6a9d4b42f4855"
        "讃岐" -> "https://follow.yahoo.co.jp/themes/02ec642844c09efa5650"
        "千葉" -> "https://follow.yahoo.co.jp/themes/038aae07d6070bad3702"
        "東京Ｖ" -> "https://follow.yahoo.co.jp/themes/000ae6f45e2481d63828"
        "金沢" -> "https://follow.yahoo.co.jp/themes/09582bedeaa6d0597615"
        "町田" -> "https://follow.yahoo.co.jp/themes/0c1a8a697a8cd53b3612"
        "熊本" -> "https://follow.yahoo.co.jp/themes/031dc9c6d60f1e554982"
        "群馬" -> "https://follow.yahoo.co.jp/themes/0e3c1cd4c068ca553533"
        "横浜Ｃ" -> "https://follow.yahoo.co.jp/themes/005fe68860d0a7cf3923"
        "名古屋" -> "https://follow.yahoo.co.jp/themes/010984813b836cc93328"
        "岐阜" -> "https://follow.yahoo.co.jp/themes/039aaf5b315723f54596"
        "京都" -> "https://follow.yahoo.co.jp/themes/052369d0cd6803123229"
        "岡山" -> "https://follow.yahoo.co.jp/themes/0d21e1411840d7d94695"
        "愛媛" -> "https://follow.yahoo.co.jp/themes/0beb2a057bde3e784809"
        "長崎" -> "https://follow.yahoo.co.jp/themes/0e43011e7a4f342b4949"
        "沼津" -> "https://follow.yahoo.co.jp/themes/05b6151e057690a34447"
        "栃木" -> "https://follow.yahoo.co.jp/themes/0f63ab146bb622654383"
        "琉球" -> "https://follow.yahoo.co.jp/themes/074e45adaffa8bc39675"
        "富山" -> "https://follow.yahoo.co.jp/themes/09a08261f6883fd64550"
        "長野" -> "https://follow.yahoo.co.jp/themes/04ae0c1cd0c54bd87700"
        "北九州" -> "https://follow.yahoo.co.jp/themes/0d49472ef6b17a314886"
        "鳥取" -> "https://follow.yahoo.co.jp/themes/06594dbf586c2c014648"
        "秋田" -> "https://follow.yahoo.co.jp/themes/08277fafba455a217434"
        "福島" -> "https://follow.yahoo.co.jp/themes/04d650e749513b483612"
        "Ｙ横浜" -> "https://follow.yahoo.co.jp/themes/05ec2038c45124950041"
        "藤枝" -> "https://follow.yahoo.co.jp/themes/0c2549eb94ecf5ce0452"
        "相模原" -> "https://follow.yahoo.co.jp/themes/0360a0cc617c9c4e0298"
        "鹿児島" -> "https://follow.yahoo.co.jp/themes/024476e4fd08963a6430"
        "盛岡" -> "https://follow.yahoo.co.jp/themes/0279ed51de2c87282435"
        else -> ""
    }
}
