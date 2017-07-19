package ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import java.util.Arrays;

import ru.alexandrkutashov.currencyconvertertestapp.business.main.CurrencyInteractor;
import ru.alexandrkutashov.currencyconvertertestapp.business.main.model.ConversionResult;
import ru.alexandrkutashov.currencyconvertertestapp.business.main.model.CurrencyListResult;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.view.CurrencyView;

/**
 * Created by Alexandr on 14.07.2017.
 */

public class CurrencyPresenterImpl implements CurrencyPresenter {

    private Context context;
    private CurrencyInteractor currencyInteractor;
    private CurrencyView currencyView;

    public CurrencyPresenterImpl(Context context, CurrencyInteractor currencyInteractor) {
        this.context = context;
        this.currencyInteractor = currencyInteractor;
    }

    @Override
    public void onStart() {
        currencyInteractor.updateCurrencies(new UpdateCurrenciesCallback() {
            @Override
            public void onCurrenciesUpdated() {
                if (currencyView != null) {
                    ((AppCompatActivity) currencyView).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateCurrencyLists();
                        }
                    });
                }
            }

            @Override
            public void onError(final String error) {
                if (currencyView != null) {
                    ((AppCompatActivity) currencyView).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            currencyView.showError(error);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void bindView(CurrencyView currencyView) {
        this.currencyView = currencyView;
        showProgress(true);
        updateCurrencyLists();
        showProgress(false);
    }

    private void showProgress(boolean flag) {
        if (currencyView != null) {
            if (flag) {
                currencyView.showProgress();
                currencyView.hideConvertButton();
            } else {
                currencyView.hideProgress();
                currencyView.showConvertButton();
            }
        }
    }

    @Override
    public void updateCurrencyLists() {
        CurrencyListResult result = currencyInteractor.getCurrencyList();
        if (currencyView != null) {
            if (TextUtils.isEmpty(result.getError())) {
                if (result.getCurrencyList().size() != 0) {
                    currencyView.setAdapter(new ArrayAdapter<>(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            Arrays.copyOf(result.getCurrencyList().toArray(),
                                    result.getCurrencyList().size(), CurrencyModel[].class)));
                }
            } else {
                currencyView.showError(result.getError());
            }
        }
    }

    @Override
    public void unbindView() {
        currencyView = null;
    }

    @Override
    public void onRevertButtonClicked(int fromCurrencyPosition, int toCurrencyPosition) {
        if (currencyView != null) {
            currencyView.updateSpinnersSelection(toCurrencyPosition, fromCurrencyPosition);
        }
    }

    @Override
    public void onConvertButtonClicked(CurrencyModel fromCurrency, CurrencyModel toCurrency, String amount) {
        showProgress(true);
        ConversionResult result = currencyInteractor.convertCurrency(fromCurrency, toCurrency, amount);

        if (currencyView != null) {
            if (TextUtils.isEmpty(result.getError())) {
                currencyView.showResult(String.valueOf(result.getResult()));
            } else {
                currencyView.showError(result.getError());
            }
        }

        showProgress(false);
    }
}
