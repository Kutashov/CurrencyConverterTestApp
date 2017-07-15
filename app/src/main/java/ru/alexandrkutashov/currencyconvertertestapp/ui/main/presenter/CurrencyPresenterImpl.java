package ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter;

import android.widget.Spinner;

import ru.alexandrkutashov.currencyconvertertestapp.ui.main.view.CurrencyView;

/**
 * Created by Alexandr on 14.07.2017.
 */

public class CurrencyPresenterImpl implements CurrencyPresenter {


    @Override
    public void bindView(CurrencyView currencyView) {

    }

    @Override
    public void unbindView() {

    }

    @Override
    public void onCreateView(CurrencyView languageView) {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void subscribeFromSpinner(Spinner spinner) {

    }

    @Override
    public void subscribeToSpinner(Spinner spinner) {

    }

    @Override
    public int getSelectionFrom() {
        return 0;
    }

    @Override
    public int getSelectionTo() {
        return 0;
    }

    @Override
    public void onTranslationRequest(String text) {

    }

    @Override
    public void setFromLanguage(String fromLanguage) {

    }

    @Override
    public void setToLanguage(String toLanguage) {

    }

    @Override
    public String getFromLanguage() {
        return null;
    }

    @Override
    public String getToLanguage() {
        return null;
    }

    @Override
    public void onRevertButtonClicked() {

    }
}
