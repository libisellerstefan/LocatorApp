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

import java.util.List;


public class SelectPois extends ListActivity {

    public static List<MarkerOptions> OptionList;
    public static String[] OptionListStrings;
    public static ArrayAdapter<String> adpt;
    public static Context thisWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisWindow = this;
        setContentView(R.layout.activity_select_pois);


/*
        adpt = new ArrayAdapter<String>(this, R.layout.activity_select_pois,OptionListStrings);
        setListAdapter(adpt);

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
*/

        try {
            (new DownloadPoisClient()).execute("http://data.wien.gv.at/daten/geo?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:SEHENSWUERDIGOGD&srsName=EPSG:4326&outputFormat=json");
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}

