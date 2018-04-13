package edu.stanford.cs108.cityinformation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Handles the LOOKUP CITIES Button and moves the user
     * to a new Activity (lookup city view).
     * @param view
     */
    public void lookupCity(View view){
        Intent intent = new Intent(this, LookupCitiesActivity.class);
        startActivity(intent);
    }

    /** React to ADD CITY Button and takes
     * the view to a new Activity to be able
     * to add a city to the database.
     * @param view
     */
    public void addCity(View view){
        Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }

    /** Handles the RESET DATABASE Button which
     * resets the SQL database to the initial state
     * with enteries in cities.txt
     * @param view
     */
    public void reset(View view){
        SingletonDataBase singletonDataBase = SingletonDataBase.getInstance(this);
        singletonDataBase.reset();
        Toast toast = Toast.makeText(getApplicationContext(), "Database Reset", Toast.LENGTH_SHORT);
        toast.show();
    }


}
