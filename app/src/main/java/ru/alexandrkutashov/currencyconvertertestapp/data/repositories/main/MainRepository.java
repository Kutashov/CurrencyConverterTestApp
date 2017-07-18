package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.CurrencyResponse;

/**
 * Created by Alexandr on 15.07.2017.
 */

public interface MainRepository {

    void updateCurrencies();
    CurrencyResponse getCurrencies(String id);
}
