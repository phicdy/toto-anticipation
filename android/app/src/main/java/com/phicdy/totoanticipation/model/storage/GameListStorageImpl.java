package com.phicdy.totoanticipation.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phicdy.totoanticipation.model.Game;

import java.util.ArrayList;
import java.util.List;

public class GameListStorageImpl implements GameListStorage {

    private SharedPreferences preferences;
    private static final String KEY_PREF = "keyPref";
    private static final String KEY_TOTO_NUM = "keyTotoNum";
    private static final String KEY_LIST = "keyList";
    public GameListStorageImpl(@NonNull Context context) {
        preferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public String totoNum() {
        return preferences.getString(KEY_TOTO_NUM, "");
    }

    @Override
    public List<Game> list(@NonNull String totoNum) {
        String storedNum = preferences.getString(KEY_TOTO_NUM, "");
        if (!storedNum.equals(totoNum)) return new ArrayList<>();
        return new Gson().fromJson(preferences.getString(KEY_LIST, ""),
                new TypeToken<List<Game>>(){}.getType());
    }

    @Override
    public void store(@NonNull String totoNum, @NonNull List<Game> list) {
        preferences.edit().putString(KEY_TOTO_NUM, totoNum).apply();
        preferences.edit().putString(KEY_LIST, new Gson().toJson(list)).apply();
    }
}