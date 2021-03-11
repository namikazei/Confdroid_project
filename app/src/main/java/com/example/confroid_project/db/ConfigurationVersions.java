package com.example.confroid_project.db;

public class ConfigurationVersions {
    int id;
    String app_id;
    int version;
    String date;
    String value;

    ConfigurationVersions(int id, String app_id, int version, String value, String date) {
        this.id = id;
        this.app_id = app_id;
        this.version = version;
        this.date = date;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
