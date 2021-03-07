package com.sevketbuyukdemir.soccerleague.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Shared Preferences are very good exercise for store user selections. I use sp's for
 * keep theme information which is selected by user.
 * And when app is start working app set selected theme as app theme. For see this effect
 * try click floating action button which is contain manage icon and destroy app.
 * When app start your last selection is become app theme.
 *
 * WARNING!!!
 * Don't use SharedPreferences as Database.
 * Don't store secure information in SharedPreferences.
 * Right usage is for example theme. :D
 */
public class SharedPrefsController
{
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    public SharedPrefsController(Context c) {
        sp = PreferenceManager.getDefaultSharedPreferences(c);
        edit = sp.edit();
    }

    public void save(String key, String val) {
        edit.putString(key, val);
        edit.commit();
    }

    public String get(String key) {
        return sp.getString(key, "");
    }
}