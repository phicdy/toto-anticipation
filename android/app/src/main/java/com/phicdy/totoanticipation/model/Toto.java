package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class Toto {
    public String number;
    public Date deadline;

    public Toto(@NonNull String number, @NonNull Date deadline) {
        this.number = number;
        this.deadline = deadline;
    }
}
