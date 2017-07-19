package ru.alexandrkutashov.currencyconvertertestapp.business.main.model;

import java.util.List;

import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;

/**
 * Created by Alexandr on 19.07.2017.
 */

public class CurrencyListResult {

    private List<CurrencyModel> currencyList;

    private String error;

    public CurrencyListResult(List<CurrencyModel> currencyList) {
        this.currencyList = currencyList;
    }

    public CurrencyListResult(String error) {
        this.error = error;
    }

    public List<CurrencyModel> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<CurrencyModel> currencyList) {
        this.currencyList = currencyList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
