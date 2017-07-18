package ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter;

import ru.alexandrkutashov.currencyconvertertestapp.business.main.CurrencyInteractor;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.view.CurrencyView;

/**
 * Created by Alexandr on 14.07.2017.
 */

public class CurrencyPresenterImpl implements CurrencyPresenter {

    private CurrencyInteractor currencyInteractor;

    public CurrencyPresenterImpl(CurrencyInteractor currencyInteractor) {
        this.currencyInteractor = currencyInteractor;
    }

    @Override
    public void bindView(CurrencyView currencyView) {

    }

    @Override
    public void unbindView() {

    }

    @Override
    public void onRevertButtonClicked() {

    }

    @Override
    public void onConvertButtonClicked(CurrencyModel fromCurrency, CurrencyModel toCurrency, String amount) {

    }
}
