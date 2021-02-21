package com.example.confroid_project.db;

public class Config {
    int id;
    int app_id;
    int version;
    String date;
    byte[] value;

    Config(int id, int app_id, int version, byte[] value, String date) {
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

    public byte[] getValue() {
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

    public void setValue(byte[] value) {
        this.value = value;
    }
}
