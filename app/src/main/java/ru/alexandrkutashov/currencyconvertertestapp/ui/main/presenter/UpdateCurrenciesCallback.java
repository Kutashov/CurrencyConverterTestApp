package ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter;

/**
 * Created by Alexandr on 18.07.2017.
 */

public interface UpdateCurrenciesCallback {

    void onCurrenciesUpdated();
    void onError(String error);
}
