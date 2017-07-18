package ru.alexandrkutashov.currencyconvertertestapp.business.main.model;

/**
 * Created by Alexandr on 18.07.2017.
 */

public class ConversionResult {

    private float result;

    private String error = null;

    public ConversionResult(float result) {
        this.result = result;
    }

    public ConversionResult(String error) {
        this.error = error;
    }

    public float getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
