package pt.isel.pdm.adeetc.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Original ideas taken from:
 * https://www.grokkingandroid.com/android-tutorial-writing-your-own-content-provider/
 */

public class ADEETCDbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = ADEETCSchema.DB_NAME;
    private static final int VERSION = ADEETCSchema.DB_VERSION;

    public ADEETCDbOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteDb(db);
        createDb(db);
    }

    private void createDb(SQLiteDatabase db) {
        db.execSQL(ADEETCSchema.Lecturers.DDL_CREATE_TABLE);
    }

    private void deleteDb(SQLiteDatabase db) {
        db.execSQL(ADEETCSchema.Lecturers.DDL_DROP_TABLE);
    }
}
