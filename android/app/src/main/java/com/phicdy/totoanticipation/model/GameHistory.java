package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

public class GameHistory {
    public static String url(@NonNull String homeTeam, @NonNull String awayTeam) {
        return "http://footballgeist.com/team/" + homeTeam +
                "&id=condition&gegener=" + awayTeam + "#byteam";
    }
}
