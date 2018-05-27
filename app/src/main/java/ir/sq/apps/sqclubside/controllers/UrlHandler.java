package ir.sq.apps.sqclubside.controllers;

/**
 * Created by Mohammad on 5/27/2018.
 */

public enum UrlHandler {
    createUserURL("http://192.168.43.82:8080/api/clubs/123"),
    updateUserURL("");
    private String url;

    private UrlHandler(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
