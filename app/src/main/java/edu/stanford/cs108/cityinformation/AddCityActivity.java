package edu.stanford.cs108.cityinformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCityActivity extends AppCompatActivity {

    private EditText cityName, contName, popSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        cityName = (EditText) findViewById(R.id.cityName);
        contName = (EditText) findViewById(R.id.continentName);
        popSize = (EditText) findViewById(R.id.populationSize);
    }

    public void addNewCity(View view){
        String cityNameEntry = cityName.getText().toString();
        String contNameEntry = contName.getText().toString();
        int popSizeEntry = Integer.parseInt(popSize.getText().toString());
        SingletonDataBase singletonDataBase = SingletonDataBase.getInstance(this);
        City city = new City(cityNameEntry,contNameEntry,popSizeEntry);
        singletonDataBase.addEntry(city);
        Toast toast = Toast.makeText(getApplicationContext(), cityNameEntry+" Added", Toast.LENGTH_SHORT);
        toast.show();
        cityName.setText("");
        contName.setText("");
        popSize.setText("");
    }
}
