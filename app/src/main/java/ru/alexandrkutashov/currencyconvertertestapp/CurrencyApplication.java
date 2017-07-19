package ru.alexandrkutashov.currencyconvertertestapp;

import android.app.Application;

import ru.alexandrkutashov.currencyconvertertestapp.business.main.CurrencyInteractor;
import ru.alexandrkutashov.currencyconvertertestapp.business.main.CurrencyInteractorImpl;
import ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main.MainRepository;
import ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main.MainRepositoryImpl;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter.CurrencyPresenter;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter.CurrencyPresenterImpl;

/**
 * Created by Alexandr on 14.07.2017.
 */

public class CurrencyApplication extends Application {

    private static CurrencyApplication application;

    private MainRepository mainRepository;
    private CurrencyInteractor currencyInteractor;
    private CurrencyPresenter currencyPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        buildDependecies();
    }

    protected void buildDependecies() {
        mainRepository = new MainRepositoryImpl();
        currencyInteractor = new CurrencyInteractorImpl(this, mainRepository);
        currencyPresenter = new CurrencyPresenterImpl(this, currencyInteractor);
    }

    public static CurrencyApplication getApplication() {
        return application;
    }

    public CurrencyPresenter getCurrencyPresenter() {
        return currencyPresenter;
    }

    public CurrencyInteractor getCurrencyInteractor() { return currencyInteractor; }

    public MainRepository getMainRepository() { return mainRepository; }
}
