package com.phicdy.totoanticipation.model.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phicdy.totoanticipation.model.Game
import com.phicdy.totoanticipation.model.Toto
import java.util.Date

class GameListStorageImpl(context: Context) : GameListStorage {

    private val preferences: SharedPreferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)

    override fun totoNum(): String {
        return preferences.getString(KEY_TOTO_NUM, "") ?: ""
    }

    override fun totoDeadline(): Date {
        return Date(preferences.getLong(KEY_TOTO_DEADLINE, 0))
    }

    override fun list(totoNum: String): List<Game> {
        val storedNum = preferences.getString(KEY_TOTO_NUM, "")
        return if (storedNum != totoNum) arrayListOf() else {
            Gson().fromJson(
                    preferences.getString(KEY_LIST, ""),
                    object : TypeToken<List<Game>>() {}.type
            )
        }
    }

    override fun store(toto: Toto, list: List<Game>) {
        preferences.edit().apply {
            putString(KEY_TOTO_NUM, toto.number).apply()
            putLong(KEY_TOTO_DEADLINE, toto.deadline.time).apply()
            putString(KEY_LIST, Gson().toJson(list)).apply()
        }
    }

    companion object {
        private const val KEY_PREF = "keyPref"
        private const val KEY_TOTO_NUM = "keyTotoNum"
        private const val KEY_TOTO_DEADLINE = "keyTotoDeadline"
        private const val KEY_LIST = "keyList"
    }
}
