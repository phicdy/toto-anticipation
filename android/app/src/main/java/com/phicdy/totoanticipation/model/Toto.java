package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class Toto {
    public final String number;
    public final Date deadline;
    public static final String DEFAULT_NUMBER = "0000";

    public Toto(@NonNull String number, @NonNull Date deadline) {
        this.number = number;
        this.deadline = deadline;
    }
}
