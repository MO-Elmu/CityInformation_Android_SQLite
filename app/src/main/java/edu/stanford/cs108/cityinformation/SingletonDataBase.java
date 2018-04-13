package edu.stanford.cs108.cityinformation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by emohelw on 2/11/2018.
 */
class SingletonDataBase extends SQLiteOpenHelper{

    private static SingletonDataBase ourInstance; // Singleton Instance
    private Context context;

    //Database Info
    private static final String DATABASE_NAME = "CitiesDB";
    private static final int VERSION = 1;

    // Database Table
    private static final String TABLE_CITIES = "cities";

    // Cities Table Columns
    protected static final String CITY = "city";
    protected static final String CONTINENT = "continent";
    protected static final String POPULATION = "population";


    public static synchronized SingletonDataBase getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new SingletonDataBase(context.getApplicationContext());
        }
        return ourInstance;
    }


    private SingletonDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String crTableString = "CREATE TABLE " + TABLE_CITIES + " ("
                + CITY + " TEXT, " + CONTINENT + " TEXT, " + POPULATION + " INTEGER, "
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(crTableString);
        initializeDatabase(context.getApplicationContext());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
            onCreate(db);
        }
    }

    /** Helper method to initialize the database with the contents
     * of the file cities.txt in the raw folder.
     * @param context
     */
    private void initializeDatabase(Context context){
        SQLiteDatabase db = getWritableDatabase();
        InputStream stream = context.getResources().openRawResource(R.raw.cities);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        String insert = "INSERT INTO " + TABLE_CITIES + " VALUES (";
        db.beginTransaction();
        try {
            while((line = buffer.readLine()) != null){
                StringBuilder sb = new StringBuilder(insert);
                sb.append(line  + ");");
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    // Insert a city into the database, we don't care about duplicate
    public void addEntry(City city){
        //Open database for writing
        SQLiteDatabase db = getWritableDatabase();

        //Insert the city into the database by forming SQLite INSERt statement.

        String cityInfo = "INSERT INTO " + TABLE_CITIES + " VALUES "
                + "('" + city.getName() + "','" + city.getContinent() + "',"
                + city.getPopulation() + ",NULL);";
        db.execSQL(cityInfo);
    }

    /* Reset the Database to its initial state */
    public void reset(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        onCreate(db);
    }

    /** These 2 methods are convenience method to retrieve data from the database
     * according to a specific query parameters as specified in the android
     * query class. One return the results of the query in an ArrayList and
     * the other return a instance of a Cursor.
     * @param cityName
     * @param contName
     * @param popSize
     * @return
     */

    //Only used for testing purposes
    private ArrayList<City> getCitiesIntoArrayList(String cityName, String contName, String popSize){
        ArrayList<City> cities = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String [] whereArgs = {cityName, contName, popSize};
        String [] cols = {CITY, CONTINENT, POPULATION};
        String where = "city LIKE ? AND continent LIKE ? AND population >= ?";
        Cursor cursor = db.query(TABLE_CITIES, cols, where, whereArgs, null, null, null );
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(CITY));
                String continent = cursor.getString(cursor.getColumnIndex(CONTINENT));
                int population = cursor.getInt(cursor.getColumnIndex(POPULATION));
                City city = new City(name, continent, population);
                cities.add(city);
            } while(cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return cities;
    }

    //Condition parameter to toggle between >= or < for the population size (not the best way but
    //it fits our need.
    public Cursor getCities(String cityName, String contName, String popSize, boolean condition){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String [] whereArgs = {cityName, contName};
        String [] whereArgs12 = {cityName, contName, popSize};
        String [] cols = {CITY, CONTINENT, POPULATION, "_id"};
        String where = CITY +" LIKE ? AND " + CONTINENT +" LIKE ?";
        String where1 = CITY +" LIKE ? AND " + CONTINENT +" LIKE ? AND "+ POPULATION +" >= ?";
        String where2 = CITY +" LIKE ? AND " + CONTINENT +" LIKE ? AND "+ POPULATION +" < ?";
        if(popSize==null || popSize.trim().isEmpty()){
            cursor = db.query(TABLE_CITIES, cols, where, whereArgs, null, null, null);
        }
        else if(condition) {
            cursor = db.query(TABLE_CITIES, cols, where1, whereArgs12, null, null, null);
        } else{
            cursor = db.query(TABLE_CITIES, cols, where2, whereArgs12, null, null, null);
        }

        return cursor;
    }

}
