package edu.stanford.cs108.cityinformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LookupCitiesActivity extends AppCompatActivity {

    private EditText cityName, contName, popSize;
    private RadioGroup greaterOrLess;
    Cursor searchResult;
    private ListView results;
    private static final String[] fromArray = {"city", "continent", "population"};
    private static final int [] toArray = {R.id.citySearchRes, R.id.contSearchRes, R.id.popSearchRes};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_cities);
        cityName = (EditText) findViewById(R.id.cityName);
        contName = (EditText) findViewById(R.id.continentName);
        popSize = (EditText) findViewById(R.id.populationSize);
        greaterOrLess = (RadioGroup) findViewById(R.id.greaterORless);
        results = (ListView) findViewById(R.id.searchResultList);

    }

    public void search(View view){

        SingletonDataBase singletonDataBase = SingletonDataBase.getInstance(this);
        boolean greaterOrEqual = true;
        int populationCriteria = greaterOrLess.getCheckedRadioButtonId();
        switch(populationCriteria){
            case R.id.greaterORequal:
                greaterOrEqual = true;
                break;
            case R.id.less:
                greaterOrEqual = false;
                break;
        }

        String cityNameEntry = "%" + cityName.getText().toString() + "%";
        String contNameEntry = "%" + contName.getText().toString() + "%";
        String popSizeEntry = popSize.getText().toString();
        searchResult = singletonDataBase.getCities(cityNameEntry,contNameEntry,popSizeEntry, greaterOrEqual);
        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.custom_listview_layout, searchResult, fromArray, toArray,0);
        results.setAdapter(adapter);
    }
}
