package com.phicdy.totoanticipation.view;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;

import java.util.ArrayList;

public interface GameListView {
    void initListBy(@NonNull ArrayList<Game> games);
    void setTitleFrom(@NonNull String xxTh);
}
