package com.example.tomek.uberallesdriver.FirebaseCouldMessaging;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Connect {

    public static String call(String url) {
        final int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = openHttpConnection(url); //otwarcie strumienia wejściowego
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in); // strumień zczytujacy znaki ze strony
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static InputStream openHttpConnection(String url) throws IOException {
        InputStream in = null;
        int response = -1;
        URL url1 = new URL(url);
        URLConnection conn = url1.openConnection();
        if (!(conn instanceof HttpsURLConnection)) throw new IOException("Not An Http Connection");
        try {
            HttpsURLConnection httpconn = (HttpsURLConnection) conn;
            httpconn.setAllowUserInteraction(false);
            httpconn.setInstanceFollowRedirects(true);
            httpconn.setRequestMethod("GET");
            httpconn.connect();
            response = httpconn.getResponseCode();
            if (response == HttpsURLConnection.HTTP_OK) {
                in = httpconn.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Connection Error");
        }
        return in;
    }

}