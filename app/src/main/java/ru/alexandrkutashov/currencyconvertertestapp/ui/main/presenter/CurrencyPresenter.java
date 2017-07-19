package ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter;

import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.view.CurrencyView;

/**
 * Created by Alexandr on 14.07.2017.
 */

public interface CurrencyPresenter {

    void onStart();
    void bindView(CurrencyView currencyView);
    void unbindView();
    void onRevertButtonClicked(int fromCurrencyPosition, int toCurrencyPosition);
    void onConvertButtonClicked(CurrencyModel fromCurrency, CurrencyModel toCurrency, String amount);
    void updateCurrencyLists();
}
