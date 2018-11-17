package com.androidacademy.msk.exerciseproject.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Storage {

    private static final String SHARED_PREF =  "com.androidacademy.msk.exerciseproject.com.data.SHARED_PREF";
    private static final String KEY_IS_FIRST_APP_LAUNCH = SHARED_PREF + ".KEY_IS_FIRST_APP_LAUNCH";

    private final SharedPreferences sharedPreferences;

    public Storage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    public boolean isFirstAppLaunch() {
        boolean isFirstAppLaunch = sharedPreferences.getBoolean(KEY_IS_FIRST_APP_LAUNCH, true);

        sharedPreferences.edit()
                .putBoolean(KEY_IS_FIRST_APP_LAUNCH, false)
                .apply();

        return isFirstAppLaunch;
    }
}
