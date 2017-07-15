package ru.alexandrkutashov.currencyconvertertestapp;

import android.app.Application;

import ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter.CurrencyPresenter;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter.CurrencyPresenterImpl;

/**
 * Created by Alexandr on 14.07.2017.
 */

public class CurrencyApplication extends Application {

    private static CurrencyApplication application;
    private CurrencyPresenter currencyPresenter;
    
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        buildDependecies();
    }

    private void buildDependecies() {
        currencyPresenter = new CurrencyPresenterImpl();
    }

    public static CurrencyApplication getApplication() {
        return application;
    }

    public CurrencyPresenter getCurrencyPresenter() {
        return currencyPresenter;
    }
}
