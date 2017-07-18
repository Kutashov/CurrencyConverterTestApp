package ru.alexandrkutashov.currencyconvertertestapp.data.providers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alexandr on 16.07.2017.
 */

public class HttpGetConnection {

    public static File downloadFile(String site) throws IOException {

        URL url = new URL(site);
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        InputStream xml = connection.getInputStream();

        final File tempFile = File.createTempFile("newdata", ".xml");

        FileOutputStream output = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int bufferLength;

        while ((bufferLength = xml.read(buffer)) != -1)
            output.write(buffer, 0, bufferLength);

        output.flush();
        output.close();

        return tempFile;
    }
}
