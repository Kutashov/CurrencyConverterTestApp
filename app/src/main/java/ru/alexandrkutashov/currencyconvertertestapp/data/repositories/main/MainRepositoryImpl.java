package ru.alexandrkutashov.currencyconvertertestapp.data.repositories.main;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ru.alexandrkutashov.currencyconvertertestapp.CurrencyApplication;
import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.Currency;
import ru.alexandrkutashov.currencyconvertertestapp.data.models.network.CurrencyResponse;
import ru.alexandrkutashov.currencyconvertertestapp.data.providers.HttpGetConnection;
import ru.alexandrkutashov.currencyconvertertestapp.data.providers.LocalCache;

/**
 * Created by Alexandr on 15.07.2017.
 */

public class MainRepositoryImpl implements MainRepository {

    private static final String currencySite = "http://www.cbr.ru/scripts/XML_daily.asp";

    @Override
    public void updateCurrencies() {
        new UpdateCurrenciesTask().execute();
    }

    static class UpdateCurrenciesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                File file = HttpGetConnection.downloadFile(currencySite);

                new LocalCache(CurrencyApplication.getApplication()).setCurrencies(fileToString(file));

            } catch (IOException e) {
                e.printStackTrace();
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
    @Nullable
    public CurrencyResponse getCurrencies(String id) {
        try {
            File xmlFile = File.createTempFile("newdata", ".xml");
            stringToFile(new LocalCache(CurrencyApplication.getApplication()).getCurrencies(), xmlFile);
            return new Persister().read(CurrencyResponse.class, xmlFile);
        } catch (IOException e) {
            Log.e(MainRepositoryImpl.class.getName(), e.getMessage());
        } catch (Exception e) {
            Log.e(MainRepositoryImpl.class.getName(), e.getMessage());
        }
        return null;
    }
}
