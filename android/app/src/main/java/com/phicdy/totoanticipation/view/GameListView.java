package com.phicdy.totoanticipation.view;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.Game;

import java.util.ArrayList;

public interface GameListView {
    void initList();
    void setTitleFrom(@NonNull String xxTh);
    void startProgress();
    void stopProgress();
    void startTotoAnticipationActivity(@NonNull String totoNum);
}
