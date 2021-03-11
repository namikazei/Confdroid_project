/**
 * void addApplication(String name, String token)
 * void addConfiguration(String appName, String value)
 * int getLastVersion(String appName)
 * String getAppToken(String appName)
 * void updateConf(int id, String value)
 * int countConf(String appName)
 * Config getLastConfiguration(String appName)
 * Config getConfiguration(String appName, int version)
 * ArrayList<App> getApps()
 * ArrayList<Config> getAllAppConfiguration(String appName)
 * Config getLastConfiguration(String appName)
 * Config getConfiguration(String appName, int version)
 * ArrayList<Config> getAllAppConfiguration(String appName)
 */

package com.example.confroid_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ConfigDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "confroid";
    private static final int DB_VERSION = 5;

    private static final String APP_TABLE = "application";
    private static final String CONFIG_TABLE = "configuration";

    private static final String APP_NAME = "name";
    private static final String APP_TOKEN = "token";

    private static final String CONF_ID = "id";
    private static final String CONF_APP_ID = "app_id";
    private static final String CONF_VERSION = "version";
    private static final String CONF_CONTENT = "value";
    private static final String CONF_DATE = "date_conf";

    Context context;

    public ConfigDb(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creation des tables pour notre base de données
        String create_app_table = "CREATE TABLE " + APP_TABLE + "("
                + APP_NAME + " TEXT PRIMARY KEY UNIQUE NOT NULL,"
                + APP_TOKEN + " TEXT );";

        db.execSQL(create_app_table);

        String create_config_table = "CREATE TABLE " + CONFIG_TABLE + "("
                + CONF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + CONF_APP_ID + " TEXT NOT NULL,"
                + CONF_VERSION + " INTERGER NOT NULL,"
                + CONF_CONTENT + " TEXT,"
                + CONF_DATE + " DATETIME, "
                + " FOREIGN KEY (" + CONF_APP_ID + ") REFERENCES " + APP_TABLE + "(" + APP_NAME + "));";

        db.execSQL(create_config_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG_TABLE);
        onCreate(db);
    }

    //fonctions de gestion des configuratons

    public void addApplication(String name, String token) {
        ContentValues values = new ContentValues();
        values.put(APP_NAME, name);
        values.put(APP_TOKEN, token);
        Log.d("DB", "add app: " + values);

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.insert(APP_TABLE, null, values);
        } catch (Exception e) {
            Toast.makeText(context, "Application exists " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean addConfiguration(String appName, String value){
        ContentValues values = new ContentValues();
        int lastversion = getLastVersion(appName);
        lastversion += 1;
        values.put(CONF_APP_ID, appName);
        values.put(CONF_CONTENT, value);
        values.put(CONF_VERSION, lastversion);
        values.put(CONF_DATE, getDateTime());
        Log.d("DB", "add conf: " + values);

        SQLiteDatabase db = this.getWritableDatabase();
        long rep = db.insert(CONFIG_TABLE, null, values);
        if (rep == -1 ){
            Toast.makeText(context, " error occured when adding configuration ", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, "new configuration added with version "+lastversion, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public int getLastVersion(String appName) {
        String req = "SELECT " + CONF_VERSION + " FROM " + CONFIG_TABLE
                + " WHERE " + CONF_APP_ID + "=" + "'" + appName + "'"
                + " ORDER BY " + CONF_VERSION + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();

        int version = 0;
        Cursor cursor = db.rawQuery(req, null);

        if (cursor.moveToFirst()) {
            version = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return version;
    }

    public void updateConf(int id, String value){
        ContentValues values = new ContentValues();
        values.put(CONF_CONTENT, value);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(CONFIG_TABLE, values, CONF_ID+"=?", new String[]{String.valueOf(id)});
        Log.d("DB", "update conf: " + values);
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

    public int countConf(String appName) {
        String req = "SELECT COUNT(*) FROM " + CONFIG_TABLE
                + " WHERE " + CONF_APP_ID + "=" + "'" + appName + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        int count = 0;
        Cursor cursor = db.rawQuery(req, null);

        if (cursor.moveToFirst()) {
            count = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        return count;
    }

    public ConfigurationVersions getLastConfiguration(String appName) {

        String req = "SELECT * FROM " + CONFIG_TABLE
                + " WHERE " + CONF_APP_ID + "=" + "'" + appName + "'"
                + " ORDER BY " + CONF_VERSION + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(req, null);

        int id = 0;
        String app_id = "";
        int version = 0;
        String value = null;
        String date = "";

        if (cursor.moveToFirst()) {
            id = Integer.parseInt(cursor.getString(0));
            app_id = cursor.getString(1);
            version = Integer.parseInt(cursor.getString(2));
            value = cursor.getString(3);
            date = cursor.getString(4);
        }
        cursor.close();

        return new ConfigurationVersions(id, app_id, version, value, date);
    }

    public ConfigurationVersions getConfiguration(String appName, int version) {
        String req = "SELECT * FROM " + CONFIG_TABLE
                + " WHERE " + CONF_APP_ID + "=" + "'" + appName + "'"
                + " AND " + CONF_VERSION + "=" + "'" + version + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(req, null);

        int id = 0;
        String app_id = "";
        int ver = 0;
        String value = null;
        String date = "";

        if (cursor.moveToFirst()) {
            id = Integer.parseInt(cursor.getString(0));
            app_id = cursor.getString(1);
            ver = Integer.parseInt(cursor.getString(2));
            value = cursor.getString(3);
            date = cursor.getString(4);
        }
        cursor.close();

        return new ConfigurationVersions(id, app_id, ver, value, date);
    }

    public ArrayList<App> getApps() {
        String req = "SELECT * FROM " + APP_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<App> apps = new ArrayList<>();

        Cursor cursor = db.rawQuery(req, null);
        Log.d("request", "get all Apps : " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String token = cursor.getString(1);

                apps.add(new App(name, token));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return apps;
    }

    public ArrayList<ConfigurationVersions> getAllAppConfiguration(String appName) {

        String req = "SELECT * FROM " + CONFIG_TABLE
                + " WHERE " + CONF_APP_ID + "=" + "'" + appName + "'"
                + " ORDER BY " + CONF_VERSION + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ConfigurationVersions> configurationVersions = new ArrayList<>();

        Cursor cursor = db.rawQuery(req, null);
        Log.d("request", "get all app conf : " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String app_id = cursor.getString(1);
                int version = Integer.parseInt(cursor.getString(2));
                String value = cursor.getString(4);
                String date = cursor.getString(5);

                configurationVersions.add(new ConfigurationVersions(id, app_id, version, value, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return configurationVersions;
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }
}
