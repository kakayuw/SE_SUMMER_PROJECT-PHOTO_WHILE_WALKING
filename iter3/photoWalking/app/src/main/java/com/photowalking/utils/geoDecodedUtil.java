package com.photowalking.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.Thread.sleep;

/*
 * Created by yuhan on 2017/9/9.
 */

public class geoDecodedUtil {
    public static String getDecodedGeoInfo(final Double dlat, final Double dlon) {
        final String[] address = {""};
        new Thread(new Runnable(){
            @Override
            public void run() {

                String lat = Double.toString(dlat);
                String lon = Double.toString(dlon);
                String decodeGeoUrl = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&mcode=60:19:A8:5F:9C:6D:52:FD:21:14:5C:A4:C4:0A:24:1D:F9:AC:28:5E;com.photowalking.main&output=json&pois=1&ak=rRCsxYdtfG8FEXHGnqLqNMErn7zFGz9o&location="
                        + lat + "," + lon ;
                try {
                    URL url = new URL(decodeGeoUrl);
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection
                            .getOutputStream(), "utf-8");
//        remember to clean up
                    out.flush();
                    out.close();
                    String res;
                    InputStream l_urlStream;
                    l_urlStream = connection.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            l_urlStream, "UTF-8"));
                    StringBuilder sb = new StringBuilder("");
                    while ((res = in.readLine()) != null) {
                        sb.append(res.trim());
                    }
                    String str = sb.toString();

                    if ( !str.isEmpty()) {
                        int addStart = str.indexOf("formatted_address\":");
                        int addEnd = str.indexOf("\",\"business");
                        if (addStart > 0 && addEnd > 0) {
                            address[0] = str.substring(addStart + 20, addEnd);
                            Log.e(address[0], address[0]);
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }}).start();
        while (address[0] == "") try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return address[0];
    }
}
