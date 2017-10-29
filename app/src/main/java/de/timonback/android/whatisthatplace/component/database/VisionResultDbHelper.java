package de.timonback.android.whatisthatplace.component.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.google.gson.Gson;

import de.timonback.android.whatisthatplace.model.VisionResult;

public class VisionResultDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VisionResult.db";

    private static final Gson gson = new Gson();

    /* Inner class that defines the table contents */
    private static class GVisionResultEntry implements BaseColumns {
        static final String TABLE_NAME = "entry";
        static final String COLUMN_NAME_IDENTIFIER = "filename";
        static final String COLUMN_NAME_RESPONSE = "response";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GVisionResultEntry.TABLE_NAME + " (" +
                    GVisionResultEntry._ID + " INTEGER PRIMARY KEY," +
                    GVisionResultEntry.COLUMN_NAME_IDENTIFIER + " TEXT," +
                    GVisionResultEntry.COLUMN_NAME_RESPONSE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GVisionResultEntry.TABLE_NAME;

    public VisionResultDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static ContentValues fromGVisionResult(VisionResult result) {
        String responseValue = gson.toJson(result);

        ContentValues values = new ContentValues();
        values.put(GVisionResultEntry.COLUMN_NAME_IDENTIFIER, result.getIdentifier());
        values.put(GVisionResultEntry.COLUMN_NAME_RESPONSE, responseValue);
        return values;
    }
}