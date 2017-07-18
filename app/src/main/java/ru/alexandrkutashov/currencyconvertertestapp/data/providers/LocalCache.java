package ru.alexandrkutashov.currencyconvertertestapp.data.providers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alexandr on 16.07.2017.
 */

public class LocalCache {

    private final static String APP_PREFERENCES = "app_preferences";
    private final static String CURRENCIES = "currencies";

    private final Context context;

    public LocalCache(Context context) {
        this.context = context;
    }

    protected SharedPreferences open() {
        return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor edit() {
        return open().edit();
    }

    public String getCurrencies() {
        return open().getString(CURRENCIES,
                "");
    }

    public void setCurrencies(String currencies) {
        edit().putString(CURRENCIES, currencies).commit();
    }
}
