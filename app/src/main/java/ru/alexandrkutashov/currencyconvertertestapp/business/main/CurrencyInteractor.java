package ru.alexandrkutashov.currencyconvertertestapp.business.main;

import ru.alexandrkutashov.currencyconvertertestapp.business.main.model.ConversionResult;
import ru.alexandrkutashov.currencyconvertertestapp.business.main.model.CurrencyListResult;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter.UpdateCurrenciesCallback;

/**
 * Created by Alexandr on 18.07.2017.
 */

public interface CurrencyInteractor {

    ConversionResult convertCurrency(CurrencyModel from, CurrencyModel to, String value);
    void updateCurrencies(UpdateCurrenciesCallback callback);
    CurrencyListResult getCurrencyList();
}
