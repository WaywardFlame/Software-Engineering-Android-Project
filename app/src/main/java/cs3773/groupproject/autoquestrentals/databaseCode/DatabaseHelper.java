package cs3773.groupproject.autoquestrentals.databaseCode;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import android.content.Context;

/**
 * A helper class for interacting with database.
 * Extends the SQLiteAssetHelper class found online.
 */
public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "autoquest_rentals_database.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Creates a DatabaseHelper instance.
     * @param context The application context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
