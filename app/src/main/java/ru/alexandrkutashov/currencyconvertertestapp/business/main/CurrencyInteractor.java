package ru.alexandrkutashov.currencyconvertertestapp.business.main;


import ru.alexandrkutashov.currencyconvertertestapp.business.main.model.ConversionResult;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;

/**
 * Created by Alexandr on 18.07.2017.
 */

public interface CurrencyInteractor {

    ConversionResult convertCurrency(CurrencyModel from, CurrencyModel to, String value);
    void updateCurrencies();
}
