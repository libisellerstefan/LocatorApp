package com.solution_driven.moc_maplocator;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
*
* Special thanks to http://www.survivingwithandroid.com/2013/01/android-async-listview-jee-and-restful.html
*/

public class DownloadPoisClient extends AsyncTask<String, Void, List<MarkerOptions>> {



    @Override
    protected void onPostExecute(List<MarkerOptions> result)        {
            super.onPostExecute(result);

        Toast.makeText(SelectPois.thisWindow, "Download completed.", Toast.LENGTH_SHORT).show();

        /*
        SelectPois.OptionList = result;

        SelectPois.OptionListStrings = new String[result.size()];
        int i=0;

        for (MarkerOptions mko : result){
            SelectPois.OptionListStrings[i] = mko.getTitle();
            i++;
        }

        //SelectPois.adpt.setItemList(result);
        SelectPois.adpt.notifyDataSetChanged();
        */
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(SelectPois.thisWindow, "Download starting..", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected List<MarkerOptions> doInBackground(String... params) {
        List<MarkerOptions> result = new ArrayList<MarkerOptions>();

        try {
            URL u = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");

            conn.connect();
            InputStream is = conn.getInputStream();

            // Read the stream
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(b) != -1)
                baos.write(b);

            String JSONResp = new String(baos.toByteArray());

            JSONArray arr = new JSONArray(JSONResp);
            for (int i=0; i < arr.length(); i++) {
                result.add(convertMarkerOptions(arr.getJSONObject(i)));
            }

            return result;
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private MarkerOptions convertMarkerOptions(JSONObject obj) throws JSONException {

        MarkerOptions mkr = new MarkerOptions();

            /*
            we use:
            - name              properties - NAME
            - coordinates       geometry - COORDINATES
            -
             */

        mkr.title(obj.getString("NAME"));
        String[] pos = obj.get("coordinates").toString().split(",");
        mkr.position(new LatLng(Double.parseDouble(pos[0]), Double.parseDouble(pos[1])));

        return mkr;
    }

}
