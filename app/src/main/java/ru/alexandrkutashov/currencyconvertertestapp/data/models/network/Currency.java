package ru.alexandrkutashov.currencyconvertertestapp.data.models.network;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Alexandr on 15.07.2017.
 */

@Root(name="Valute")
public class Currency {

    @Attribute(name="ID")
    private String id;

    @Element(name="NumCode")
    private Integer numCode;

    @Element(name="CharCode")
    private String charCode;

    @Element(name="Nominal")
    private Integer nominal;

    @Element(name="Name")
    private String name;

    @Element(name="Value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
