package pt.isel.pdm.adeetc.provider;

/*
 * Original ideas taken from:
 * https://www.grokkingandroid.com/android-tutorial-writing-your-own-content-provider/
 */

import android.provider.BaseColumns;

interface ADEETCSchema {

    String DB_NAME = "adeetc.db";
    int DB_VERSION = 1;

    String COL_ID = BaseColumns._ID;

    static interface Lecturers {
        String TBL_NAME  = "lecturers";

        String COL_CODE  = "code";
        String COL_NAME  = "name";
        String COL_EMAIL = "email";

        String DDL_CREATE_TABLE =
                "CREATE TABLE " + TBL_NAME + "(" +
                        COL_ID    + " INTEGER PRIMARY KEY, " +
                        COL_CODE  + " INTEGER UNIQUE, " +
                        COL_NAME  + " TEXT, " +
                        COL_EMAIL + " TEXT UNIQUE)";

        String DDL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TBL_NAME;
    }
}
