package com.example.szymon.app.database;

public class FeedReaderContract {

    private FeedReaderContract() {}

    public static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE IF NOT EXISTS fares(" +
                    "userPhone TEXT, " +
                    "fareId TEXT Primary Key, " +
                    "startingDate TEXT, " +
                    "startingPoint TEXT, " +
                    "endingPoint TEXT);";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS  fares;";
}
