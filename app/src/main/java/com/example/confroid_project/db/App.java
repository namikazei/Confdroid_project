package com.example.confroid_project.db;

public class App {

    int id;
    String name;
    int token;

    App(int id, String name, int token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public int getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
