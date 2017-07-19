package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

import android.support.annotation.Nullable;

import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.CurrencyResponse;

/**
 * Created by Alexandr on 15.07.2017.
 */

public interface MainRepository {

    void updateCurrencies(ExecuteCallback callback);

    @Nullable
    CurrencyResponse getCurrencies() throws Exception;
}
