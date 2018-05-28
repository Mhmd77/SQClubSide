package ir.sq.apps.sqclubside.controllers;

/**
 * Created by Mohammad on 5/27/2018.
 */

public enum UrlHandler {
    createUserURL("clubs/123"),
    updateUserURL("");
    private String url;
    private String baseUrl = "http://192.168.43.82:8080/api/";

    private UrlHandler(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return baseUrl + url;
    }
}