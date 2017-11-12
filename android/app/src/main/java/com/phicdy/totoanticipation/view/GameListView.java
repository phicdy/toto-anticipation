package com.phicdy.totoanticipation.view;

import android.support.annotation.NonNull;

public interface GameListView {
    void initList();
    void setTitleFrom(@NonNull String xxTh, @NonNull String deadline);
    void startProgress();
    void stopProgress();
    void startTotoAnticipationActivity(@NonNull String totoNum);
    void goToSetting();
}
