package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import ru.alexandrkutashov.currencyconvertertestapp.CurrencyApplication;
import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.CurrencyResponse;
import ru.alexandrkutashov.currencyconvertertestapp.data.providers.HttpGetConnection;
import ru.alexandrkutashov.currencyconvertertestapp.data.providers.LocalCache;

/**
 * Created by Alexandr on 15.07.2017.
 */

public class MainRepositoryImpl implements MainRepository {

    private static final String currencySite = "http://www.cbr.ru/scripts/XML_daily.asp";

    @Override
    public void updateCurrencies(ExecuteCallback callback) {
        new UpdateCurrenciesTask(callback).execute();
    }

    static class UpdateCurrenciesTask extends AsyncTask<Void, Void, Void> {

        private ExecuteCallback callback;

        private UpdateCurrenciesTask() {}

        public UpdateCurrenciesTask(ExecuteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                File file = HttpGetConnection.downloadFile(currencySite);

                new LocalCache(CurrencyApplication.getApplication()).setCurrencies(fileToString(file));

                callback.onExecuted();
            } catch (IOException e) {
                callback.onExecuteError(e);
            }
            return null;
        }
    }

    @NonNull
    private static String fileToString(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);

        BufferedReader reader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static void stringToFile(String fileContents, File file) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            Log.e(MainRepositoryImpl.class.getName(), e.getMessage());
        }
    }


    @Override
    public CurrencyResponse getCurrencies() throws Exception {
        File xmlFile = File.createTempFile("newdata", ".xml");
        stringToFile(new LocalCache(CurrencyApplication.getApplication()).getCurrencies(), xmlFile);
        return new Persister().read(CurrencyResponse.class, xmlFile);
    }
}
