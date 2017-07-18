package ru.alexandrkutashov.currencyconvertertestapp.business.main;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ru.alexandrkutashov.currencyconvertertestapp.R;
import ru.alexandrkutashov.currencyconvertertestapp.business.main.model.ConversionResult;
import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.Currency;
import ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main.MainRepository;
import ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main.UpdateCurrenciesCallback;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;

/**
 * Created by Alexandr on 18.07.2017.
 */

public class CurrencyInteractorImpl implements CurrencyInteractor {

    private Context context;
    private MainRepository mainRepository;
    private HashMap<String, Currency> currencyList = null;

    public CurrencyInteractorImpl(Context context, MainRepository mainRepository) {
        this.context = context;
        this.mainRepository = mainRepository;
    }

    @Override
    public ConversionResult convertCurrency(CurrencyModel from, CurrencyModel to, String value) {

        try {
            HashMap<String, Currency> currencies = getCurrencyList();

            if (currencies.size() == 0) {
                return new ConversionResult(context.getString(R.string.no_internet));
            }

            float val = Float.parseFloat(value.replace(',', '.'));
            float fromValue = Float.parseFloat(currencies.get(from.getId()).getValue().replace(',', '.'));
            float toValue = Float.parseFloat(currencies.get(to.getId()).getValue().replace(',', '.'));
            return new ConversionResult(val * fromValue / toValue);
        } catch (IOException e) {
            e.printStackTrace();
            return new ConversionResult(context.getString(R.string.no_internet));
        } catch (Exception e) {
            e.printStackTrace();
            return new ConversionResult(context.getString(R.string.undefined_error));
        }
    }

    private HashMap<String, Currency> getCurrencyList() throws Exception {
        if (currencyList == null) {
            updateCurrencyList();
        }
        return currencyList;
    }

    private void updateCurrencyList() throws Exception {
        if (currencyList != null) {
            currencyList.clear();
        }

        List<Currency> items = mainRepository.getCurrencies().getCurrencyList();
        for (Currency item : items) {
            currencyList.put(item.getId(), item);
        }
    }

    @Override
    public void updateCurrencies() {
        mainRepository.updateCurrencies(new UpdateCurrenciesCallback() {
            @Override
            public void onCurrenciesUpdated() {
                try {
                    updateCurrencyList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCurrenciesUpdateError(Exception e) {

            }
        });
    }
}
