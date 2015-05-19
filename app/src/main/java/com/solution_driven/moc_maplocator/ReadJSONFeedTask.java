package com.solution_driven.moc_maplocator;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
*
* Special thanks to:
*    Adrian Bolonio
*    http://www.survivingwithandroid.com/2013/01/android-async-listview-jee-and-restful.html
*    http://mobiforge.com/design-development/consuming-json-services-android-apps
*/


    public class ReadJSONFeedTask extends AsyncTask
            <String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Info msg for user
            Toast.makeText(SelectPois.thisWindow, "Download starting..", Toast.LENGTH_SHORT).show();

        }

        protected String doInBackground(String... urls) {
            return getJSONFeed(urls[0]);
        }

        protected void onPostExecute(String result) {

            int poicount=0;

            try {


                JSONObject json = new JSONObject(result);
                JSONArray features = json.getJSONArray("features");

                poicount = features.length();

                for (int i=0; i < features.length(); i++) {

                    MarkerOptions mkro = new MarkerOptions();

                    JSONObject json_feature = features.getJSONObject(i);
                    String name = json_feature.getJSONObject("properties").getString("NAME");
                    String lon = json_feature.getJSONObject("geometry").getJSONArray("coordinates").getString(0);
                    String lat = json_feature.getJSONObject("geometry").getJSONArray("coordinates").getString(1);

                    //add to point
                    mkro.title(name);
                    mkro.position(new LatLng(Double.parseDouble(lon), Double.parseDouble(lat)));
                    SelectPois.PoiArray.add(name);


                }


            } catch (Exception e) {
                Log.d("ReadJSONFeedTask", e.getLocalizedMessage());
            }

            if (result!=null && result.length() >0) {
                Toast.makeText(SelectPois.thisWindow, "Download completed. " + poicount + " POIs downloaded.", Toast.LENGTH_SHORT).show();

            }
            else{
                SelectPois.PoiArray.clear();
                SelectPois.PoiArray.add("<No data found>");
            }

            //SelectPois.adpt.notifyDataSetChanged();

        }


        private String getJSONFeed(String URL) {
            StringBuilder myString = new StringBuilder();
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        myString.append(line);

                    }
                    inputStream.close();
                } else {
                    Log.d("[ERROR]", "Failed to download file");
                    Toast.makeText(SelectPois.thisWindow, "Failed to download file", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d("[readJSONFeedException]", e.getLocalizedMessage());
                Toast.makeText(SelectPois.thisWindow, "Failed to download file", Toast.LENGTH_SHORT).show();
            }
            return myString.toString();
        }
    }



