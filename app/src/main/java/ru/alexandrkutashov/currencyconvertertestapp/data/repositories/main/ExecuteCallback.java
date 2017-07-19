package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

/**
 * Created by Alexandr on 18.07.2017.
 */

public interface ExecuteCallback {

    void onExecuted();
    void onExecuteError(Exception e);
}
