package test.locationmarkers.custom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import test.locationmarkers.pojo.LocationDetails;

/**
 * Created by tanmay on 22/07/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "locationDetails";

    // Contacts table name
    private static final String TABLE_DETAILS = "details";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_DESCRIPTION = "description";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL,"
                + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
        // Create tables again
        onCreate(db);
    }

    public void addLocation(LocationDetails location,Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, location.getName());
        values.put(KEY_LATITUDE, location.getLatitude());
        values.put(KEY_LONGITUDE, location.getLongitude());
        values.put(KEY_DESCRIPTION,location.getDescription());

        // Inserting Row
        db.insert(TABLE_DETAILS, null, values);
        Utilities.toast(context,location.getName()+ " Inserted");
        db.close(); // Closing database connection
    }

    public List<LocationDetails> getAllContacts() {
        List<LocationDetails> locationDetailsList = new ArrayList<LocationDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationDetails locationDetails = new LocationDetails();
                locationDetails.setId(Integer.parseInt(cursor.getString(0)));
                locationDetails.setName(cursor.getString(1));
                locationDetails.setLatitude(cursor.getDouble(2));
                locationDetails.setLongitude(cursor.getDouble(3));
                locationDetails.setDescription(cursor.getString(4));
                // Adding locationDetails to list
                locationDetailsList.add(locationDetails);
            } while (cursor.moveToNext());
        }

        // return locationDetails list
        return locationDetailsList;
    }
}
