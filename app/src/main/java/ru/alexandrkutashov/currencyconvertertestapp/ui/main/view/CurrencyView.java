package ru.alexandrkutashov.currencyconvertertestapp.ui.main.view;

import android.widget.ArrayAdapter;

/**
 * Created by Alexandr on 14.07.2017.
 */

public interface CurrencyView {

    void setAdapter(ArrayAdapter adapter);
    void updateSpinnersSelection(int positionCurrencyFrom, int positionCurrencyTo);
    void showResult(String text);
    void showError(String text);
    void showProgress();
    void hideProgress();
    void showConvertButton();
    void hideConvertButton();

}
