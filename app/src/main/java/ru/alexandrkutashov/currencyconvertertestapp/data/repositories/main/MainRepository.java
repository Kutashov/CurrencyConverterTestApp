package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

import android.support.annotation.Nullable;

import java.io.IOException;

import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.CurrencyResponse;

/**
 * Created by Alexandr on 15.07.2017.
 */

public interface MainRepository {

    void updateCurrencies(UpdateCurrenciesCallback callback);

    @Nullable
    CurrencyResponse getCurrencies() throws Exception;
}
