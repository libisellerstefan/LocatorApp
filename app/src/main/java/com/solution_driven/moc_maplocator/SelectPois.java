package com.solution_driven.moc_maplocator;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
*/

public class SelectPois extends ListActivity {

    public static List<MarkerOptions> OptionList;
    public static String[] OptionListStrings;
    public static ArrayAdapter<String> adpt;
    public static Context thisWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        thisWindow=this;
  /*
     //   setContentView(R.layout.activity_select_pois);

     //   adpt = new ArrayAdapter<String>(this, R.layout.list_pois,OptionListStrings);
     //   setListAdapter(adpt);

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

*/

        (new DownloadPoisClient()).execute("http://data.wien.gv.at/daten/geo?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:SEHENSWUERDIGOGD&srsName=EPSG:4326&outputFormat=json");


    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}

