package ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter;

import android.widget.Spinner;

import ru.alexandrkutashov.currencyconvertertestapp.ui.main.view.CurrencyView;

/**
 * Created by Alexandr on 14.07.2017.
 */

public interface CurrencyPresenter {

    void bindView(CurrencyView currencyView);
    void unbindView();

    void onCreateView(CurrencyView languageView);
    void onDestroyView();
    void subscribeFromSpinner(Spinner spinner);
    void subscribeToSpinner(Spinner spinner);
    int getSelectionFrom();
    int getSelectionTo();
    void onTranslationRequest(String text);
    void setFromLanguage(String fromLanguage);
    void setToLanguage(String toLanguage);
    String getFromLanguage();
    String getToLanguage();
    void onRevertButtonClicked();
}
