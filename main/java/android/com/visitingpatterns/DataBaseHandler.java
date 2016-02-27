package android.com.visitingpatterns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srinu on 2/19/2016.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "LocationDetailaDataBase.db";

    // Contacts table name
    public static final String TABLE_LOCATION = "Location_Table";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ALTITUDE = "altitude";
    public static final String ACCURACY = "accuracy";
    public static final String TIME = "time";
    public static final String ADDRESS = "address";




    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String MAIN_USER_TABLE = " CREATE TABLE " +TABLE_LOCATION
                + " (" + LATITUDE +" TEXT ,"
                + LONGITUDE + " TEXT,"
                + ALTITUDE + " TEXT,"
                + ACCURACY + " TEXT ,"
                + TIME + " TEXT ,"
                + ADDRESS + " TEXT"
                + ");";
        db.execSQL(MAIN_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        // Create tables again
        onCreate(db);
    }

    void addLocationInfo(LocationInfo locationinfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LATITUDE,locationinfo.latitude);
        values.put(LONGITUDE, locationinfo.longitude); // Contact Name
        values.put(ALTITUDE, locationinfo.altitude); // Contact Phone
        values.put(ACCURACY, locationinfo.accuracy); //Contact phone no
         values.put(TIME, locationinfo.time);
        values.put(ADDRESS, locationinfo.address);

        // Inserting Row
        db.insert(TABLE_LOCATION, null, values);
        db.close(); // Closing database connection
    }

    public List<LocationInfo> getAllLocationDetails() {
        List<LocationInfo> contactList = new ArrayList<LocationInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setLatitude(cursor.getDouble(0));
                locationInfo.setLongitude(cursor.getDouble(1));
                locationInfo.setAltitude(cursor.getDouble(2));
                locationInfo.setAccuracy(cursor.getDouble(3));
                locationInfo.setTime(cursor.getLong(4));
                locationInfo.setAddress(cursor.getString(5));
                contactList.add(locationInfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public List<LocationInfo> getAddColumnDetails(){

        List<LocationInfo> contactList = new ArrayList<LocationInfo>();
        // Select All Query
        String selectQuery = "SELECT "+ADDRESS+" FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setAddress(cursor.getString(5));
                contactList.add(locationInfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<LocationInfo> getTimeColumnDetails()
    {
        List<LocationInfo> contactList = new ArrayList<LocationInfo>();
        // Select All Query
        String selectQuery = "SELECT "+TIME+"  FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setTime(cursor.getLong(4));
                contactList.add(locationInfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;

    }

    public void deleteAllLocationInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION,null,null);
        db.close();
    }

}
