package pt.isel.pdm.adeetc.provider;

/*
 * Original ideas taken from:
 * https://www.grokkingandroid.com/android-tutorial-writing-your-own-content-provider/
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface ADEETCContract {

    String AUTHORITY = "pt.isel.pdm.adeetc.provider";

    Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    String SELECTION_BY_ID = BaseColumns._ID + " = ? ";

    String MEDIA_BASE_SUBTYPE = "/vnd.adeetc.";

    public interface Lecturers extends BaseColumns {
        String RESOURCE = "lecturers";

        Uri CONTENT_URI =
                Uri.withAppendedPath(
                        ADEETCContract.CONTENT_URI,
                        RESOURCE);

        String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        MEDIA_BASE_SUBTYPE + RESOURCE;

        String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        MEDIA_BASE_SUBTYPE + RESOURCE;

        String CODE  = "code";
        String NAME  = "name";
        String EMAIL = "email";

        String[] PROJECTION_ALL = { _ID, CODE, NAME, EMAIL };

        String DEFAULT_SORT_ORDER = NAME + " ASC";
    }
}
