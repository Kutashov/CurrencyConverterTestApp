package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

/**
 * Created by Alexandr on 18.07.2017.
 */

public interface UpdateCurrenciesCallback {

    void onCurrenciesUpdated();
    void onCurrenciesUpdateError(Exception e);
}
