package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

import java.util.List;

public interface GameListStorage {
    List<Game> list(@NonNull String totoNum);
    void store(@NonNull String totoNum, @NonNull List<Game> list);
}
