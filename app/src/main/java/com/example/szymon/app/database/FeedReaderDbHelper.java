package com.example.szymon.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.szymon.app.api.pojo.HistoryFare;

import java.util.HashMap;

import static com.example.szymon.app.database.FeedReaderContract.SQL_CREATE_ENTRIES1;
import static com.example.szymon.app.database.FeedReaderContract.SQL_DELETE_ENTRIES;


public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES1);
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

    public void insert(SQLiteDatabase db, HistoryFare fare, String user) {
        db.execSQL("insert into fares values('" +
                user + "', '" +
                fare.getFareId() + "', '" +
                fare.getStartingDate() + "', '" +
                fare.getStartingPoint() + "', '" +
                fare.getEndingPoint() + "')");
    }

    public static void insertExample(SQLiteDatabase db) {
        db.execSQL("insert into fares values('500', '-KdsgGdgdfdert4g34vRE3Ghadfh', '2047.51.18', 'Wro', 'Waw')");
        Log.i("Insert INFO", "Dodano przykałdowy rekord do tabeli");
    }

    public HashMap<String,HistoryFare> selectFareByUser(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from fares where userPhone = '" + user + "';", null);
        HashMap<String,HistoryFare> map = new HashMap<>();

        if(c.moveToFirst()){
            do{
                String fareId = c.getString(1);
                String startingDate = c.getString(2);
                String startingPoint = c.getString(3);
                String endingPoint = c.getString(4);
                HistoryFare fare = new HistoryFare(fareId,startingDate,startingPoint,endingPoint);
                map.put(fareId,fare);

            }while (c.moveToNext());
            c.close();
        }
        return map;

    }
}
