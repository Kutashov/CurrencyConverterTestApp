package ru.alexandrkutashov.currencyconvertertestapp.data;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.core.Persister;

import java.io.File;

import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.Currency;
import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.CurrencyResponse;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandr on 15.07.2017.
 */

public class XmlDataMapperTest {

    private File xmlFile;

    @Before
    public void setUp() throws Exception {
        xmlFile = new File(getClass().getClassLoader().getResource("res/XML_daily.asp.xml").getPath());
    }

    @Test
    public void checkParseXML() throws Exception {

        CurrencyResponse orderObject = new Persister().read(CurrencyResponse.class, xmlFile);

        assertEquals(34, orderObject.getCurrencyList().size());

        Currency testCurrency = orderObject.getCurrencyList().get(0);
        assertEquals("R01010", testCurrency.getId());
        assertEquals("36", testCurrency.getNumCode().toString());
        assertEquals("AUD", testCurrency.getCharCode());
        assertEquals(Integer.valueOf(1), testCurrency.getNominal());
        assertEquals("Австралийский доллар", testCurrency.getName());
        assertEquals("46,4673" , testCurrency.getValue());
    }
}
