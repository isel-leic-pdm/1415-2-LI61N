package pt.isel.pdm.adeetc.provider;

/*
 * Original ideas taken from:
 * https://www.grokkingandroid.com/android-tutorial-writing-your-own-content-provider/
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ADEETCProvider extends ContentProvider {

    private static final int LECTURERS_LST = (1 << 1);
    private static final int LECTURERS_OBJ = (1 << 1) | 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(
                ADEETCContract.AUTHORITY,
                ADEETCContract.Lecturers.RESOURCE,
                LECTURERS_LST);
        URI_MATCHER.addURI(
                ADEETCContract.AUTHORITY,
                ADEETCContract.Lecturers.RESOURCE + "/#",
                LECTURERS_OBJ);
    }

    private ADEETCDbOpenHelper dbHelper = null;

    @Override
    public boolean onCreate() {
        dbHelper = new ADEETCDbOpenHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case LECTURERS_LST:
                return ADEETCContract.Lecturers.CONTENT_TYPE;
            case LECTURERS_OBJ:
                return ADEETCContract.Lecturers.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qbuilder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case LECTURERS_LST:
                qbuilder.setTables(ADEETCSchema.Lecturers.TBL_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ADEETCContract.Lecturers.DEFAULT_SORT_ORDER;
                }
                break;
            case LECTURERS_OBJ:
                qbuilder.setTables(ADEETCSchema.Lecturers.TBL_NAME);
                qbuilder.appendWhere(ADEETCSchema.COL_ID + "=" + uri.getLastPathSegment());
                break;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = qbuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table;
        switch (URI_MATCHER.match(uri)) {
            case LECTURERS_LST:
                table = ADEETCSchema.Lecturers.TBL_NAME;
                break;
            default:
                throw new IllegalArgumentException();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = db.insert(table, null, values);

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, newId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table;
        switch (URI_MATCHER.match(uri)) {
            case LECTURERS_LST:
                table = ADEETCSchema.Lecturers.TBL_NAME;
                if (selection != null) {
                    throw new IllegalArgumentException("selection not supported");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ndel = db.delete(table, null, null);

        getContext().getContentResolver().notifyChange(uri, null);
        return ndel;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
