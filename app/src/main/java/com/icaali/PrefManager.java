package com.icaali;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String PREF_NAME = "MyAppPref";
    private static final String KEY_PURCHASE_STATUS = "purchase_status";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Simpan status
    public void savePurchaseStatus(boolean status) {
        editor.putBoolean(KEY_PURCHASE_STATUS, status);
        editor.apply();
    }

    // Ambil status
    public boolean getPurchaseStatus() {
        return sharedPreferences.getBoolean(KEY_PURCHASE_STATUS, false); // default false
    }
}

