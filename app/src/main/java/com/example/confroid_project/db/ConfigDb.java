package com.example.confroid_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
                + CONF_VERSION + " INTEGER NOT NULL,"
                + CONF_CONTENT + " BLOB,"
                + CONF_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + " FOREIGN KEY (" + CONF_APP_ID + ")" + "REFERENCES " + APP_TABLE + "(" + APP_ID + "));";

        db.execSQL(create_config_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG_TABLE);
        onCreate(db);
    }

    //fonctions de gestion des configuratons

    public void addApplication(String name, String token){
        ContentValues values = new ContentValues();
        values.put(APP_NAME, name);
        values.put(APP_TOKEN, token);
        Log.d("DB", "add app: " + values);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(APP_TABLE, null, values);
    }

    public void addConfiguration(int appID, String value){
        ContentValues values = new ContentValues();
        values.put(CONF_APP_ID, appID);
        values.put(CONF_CONTENT, value);
        values.put(CONF_VERSION, 5);
        Log.d("DB", "add conf: " + values);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(APP_TABLE, null, values);

    }

    public int getLastVersion(int appID){
        String req = "SELECT " + CONF_VERSION + " FROM " + CONFIG_TABLE
                + " ORDER BY " + CONF_VERSION + " DESC "
                + " WHERE " + CONF_APP_ID + "=" + "'" + appID + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        int version = 0;
        Cursor cursor = db.rawQuery(req, null);

        if (cursor.moveToFirst()) {
            version = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return version;
    }

    public int getAppId(String appName) {
        String req = "SELECT " + APP_ID + " FROM " + APP_TABLE
                + " WHERE " + APP_NAME + "=" + "'" + appName + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        int id = 0;
        Cursor cursor = db.rawQuery(req, null);

        if (cursor.moveToFirst()) {
            id = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return id;
    }

    public String getAppToken(String appName) {
        String req = "SELECT " + APP_TOKEN + " FROM " + APP_TABLE
                + " WHERE " + APP_NAME + "=" + "'" + appName + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        String token = "";
        Cursor cursor = db.rawQuery(req, null);

        if (cursor.moveToFirst()) {
            token = cursor.getString(0);
        }
        cursor.close();
        return token;
    }

    public String getLastConfiguration(String appName) {

        String req = "SELECT " + APP_TOKEN + " FROM " + APP_TABLE
                + " ORDER BY " + CONF_VERSION + " DESC "
                + " WHERE " + APP_NAME + "=" + "'" + appName + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        String token = "";
        Cursor cursor = db.rawQuery(req, null);

        if (cursor.moveToFirst()) {
            token = cursor.getString(0);
        }
        cursor.close();
        return token;
    }

    public String getConfigurationV(String appName, int version){
        return null;
    }

    public ArrayList<Config> getAllAppConfiguration(String appName){
        int appID = getAppId(appName);

        String req = "SELECT * FROM " + CONFIG_TABLE
                + " WHERE " + CONF_APP_ID + "=" + "'" + appID + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Config> configs = new ArrayList<>();

        Cursor cursor = db.rawQuery(req, null);
        Log.d("request", "get all app conf : " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                int app_id = Integer.parseInt(cursor.getString(1));
                int version = Integer.parseInt(cursor.getString(2));
                byte[] value = cursor.getBlob(4);
                String date = cursor.getString(5);

                configs.add(new Config(id,app_id,version,value,date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return configs;
    }

    public ArrayList<Config> getAllConfiguration(){
        String req = "SELECT * FROM " + CONFIG_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Config> configs = new ArrayList<>();

        Cursor cursor = db.rawQuery(req, null);
        Log.d("request", "get all conf : " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                int app_id = Integer.parseInt(cursor.getString(1));
                int version = Integer.parseInt(cursor.getString(2));
                byte[] value = cursor.getBlob(4);
                String date = cursor.getString(5);

                configs.add(new Config(id,app_id,version,value,date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return configs;
    }
}
