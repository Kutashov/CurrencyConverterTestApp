package ru.alexandrkutashov.currencyconvertertestapp.ui.main.model;


/**
 * Created by Alexandr on 18.07.2017.
 */

public class CurrencyModel {

    private String id;

    private String charCode;

    public CurrencyModel(String id, String charCode) {
        this.id = id;
        this.charCode = charCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    @Override
    public String toString() {
        return charCode;
    }
}
