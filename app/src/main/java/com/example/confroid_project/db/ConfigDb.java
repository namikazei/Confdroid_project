package com.example.confroid_project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConfigDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "confroid";
    private static final int DB_VERSION = 5;

    private static final String APP_TABLE = "application";
    private static final String CONFIG_TABLE = "configuration";

    private static final String APP_ID = "id";
    private static final String APP_NAME = "name";
    private static final String APP_TOKEN = "token";

    private static final String CONF_ID = "id";
    private static final String CONF_APP_ID = "app_id";
    private static final String CONF_VERSION = "version";
    private static final String CONF_CONTENT = "value";
    private static final String CONF_DATE = "date_conf";

    public ConfigDb(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creation des table pour notre base de données
        String create_app_table = "CREATE TABLE " + APP_TABLE + "("
                + APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + APP_NAME + " TEXT,"
                + APP_TOKEN + " TEXT );";

        db.execSQL(create_app_table);

        String create_config_table = "CREATE TABLE " + CONFIG_TABLE + "("
                + CONF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + CONF_APP_ID + " INTEGER NOT NULL,"
                + CONF_VERSION + " INTEGER,"
                + CONF_CONTENT + " TEXT,"
                + CONF_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + " FOREIGN KEY (" + CONF_APP_ID + ")" + "REFERENCES " + APP_TABLE + "(" + APP_ID + "));";

        db.execSQL(create_config_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
