package com.solution_driven.moc_maplocator;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class SelectPois extends ListActivity {

    public static List<MarkerOptions> OptionList = new ArrayList<>();
    public static List<String> PoiArray;
    public static ArrayAdapter<String> adpt;
    public static Context thisWindow;
    public static ListView MyListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init current contexts
        thisWindow = this;
        setContentView(R.layout.activity_select_pois);
        MyListView=(ListView) findViewById(R.id.myListView);


        //Init the data list
        PoiArray = new ArrayList<String>();
        SelectPois.PoiArray.add("<No data downloaded yet>");


        adpt = new ArrayAdapter<String>(this, R.layout.list_pois,PoiArray);
        setListAdapter(adpt);

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);


        try {
            //http://data.wien.gv.at/daten/geo?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:SCHIFFANLEGESTOGD&srsName=EPSG:4326&outputFormat=json

            //"http://data.wien.gv.at/daten/wfs?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:TRINKBRUNNENOGD&srsName=EPSG:4326&outputFormat=json";

            (new ReadJSONFeedTask()).execute("http://data.wien.gv.at/daten/geo?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:SCHIFFANLEGESTOGD&srsName=EPSG:4326&outputFormat=json");

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}

