package com.phicdy.totoanticipation.model.storage;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.Toto;

import java.util.List;

public interface GameListStorage {
    String totoNum();
    List<Game> list(@NonNull String totoNum);
    void store(@NonNull Toto toto, @NonNull List<Game> list);
}
