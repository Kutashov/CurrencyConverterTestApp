package ru.alexandrkutashov.currencyconvertertestapp.data.models.network;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr on 15.07.2017.
 */

@Root(name = "ValCurs", strict=false)
public class CurrencyResponse {

    @ElementList(required = true, inline = true, name="Valute")
    private List<Currency> list;


    public CurrencyResponse() {
        this.list = new ArrayList<>();
    }


    public List<Currency> getCurrencyList() {
        return list;
    }
}
