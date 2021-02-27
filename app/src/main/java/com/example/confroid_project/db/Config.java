package com.example.confroid_project.db;

import org.json.JSONObject;

public class Config {
    int id;
    int app_id;
    int version;
    String date;
    JSONObject value;

    Config(int id, int app_id, int version, JSONObject value, String date) {
        this.id = id;
        this.app_id = app_id;
        this.version = version;
        this.date = date;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getApp_id() {
        return app_id;
    }

    public int getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    public JSONObject getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setValue(JSONObject value) {
        this.value = value;
    }
}
