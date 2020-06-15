package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.classes.LocationClass;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationDB";
    private static final String TABLE_LOCATION = "location";
    private static final String KEY_ID = "id";
    private static final String KEY_LATITUDE= "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_TIMESTAMP = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOCATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LATITUDE+ " TEXT,"
                + KEY_LONGITUDE + " TEXT," + KEY_TIMESTAMP+ " TEXT"+")";
        db.execSQL(CREATE_LOCATION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        // Create tables again
        onCreate(db);
    }

    // code to add the new location1
    public void addLocation(LocationClass location1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, location1.getLatitude()); // LocationClass Name
        values.put(KEY_LONGITUDE, location1.getLongitude()); // LocationClass Phone
        values.put(KEY_TIMESTAMP, location1.getTime()); // LocationClass Phone

        // Inserting Row
        db.insert(TABLE_LOCATION, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single location1
    LocationClass getLocationClass(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATION, new String[] { KEY_ID,
                        KEY_LATITUDE, KEY_LONGITUDE,KEY_TIMESTAMP }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationClass location1 = new LocationClass(Integer.parseInt(cursor.getString(0)),Double.parseDouble(cursor.getString(1)),
                Double.parseDouble(cursor.getString(2)), cursor.getString(3));
        // return location1
        return location1;
    }

    // code to get all location1s in a list view
    public List<LocationClass> getAllLocationClasss() {
        List<LocationClass> location1List = new ArrayList<LocationClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
//        Log.i("delete", "delete Tabel");
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationClass location1 = new LocationClass();
                location1.setID(Integer.parseInt(cursor.getString(0)));
                location1.setLatitude(Double.parseDouble(cursor.getString(1)));
                location1.setLongitude(Double.parseDouble(cursor.getString(2)));
                location1.setTime(cursor.getString(3));
                // Adding location1 to list
                location1List.add(location1);
            } while (cursor.moveToNext());
        }

        // return location1 list
        return location1List;
    }

    // code to update the single location1
    public int updateLocationClass(LocationClass location1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, location1.getLatitude());
        values.put(KEY_LONGITUDE, location1.getLongitude());
        values.put(KEY_TIMESTAMP, location1.getTime());

        // updating row
        return db.update(TABLE_LOCATION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(location1.getID()) });
    }

    // Deleting single location1
    public void deleteLocationClass(LocationClass location1) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION, KEY_ID + " = ?",
                new String[] { String.valueOf(location1.getID()) });
        db.close();
    }

    // Getting location1s Count
    public int getLocationClasssCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}