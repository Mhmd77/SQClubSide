package ir.sq.apps.sqclubside.controllers;

import android.util.Log;

/**
 * Created by Mohammad on 5/27/2018.
 */

public enum UrlHandler {

    signUpUserURL("users"),
    signInUserURL("users/login"),
    createUserURL("clubs", false, true),
    getTagsURL("clubs/tags"),
    uploadeImageURL("images", true, true);

    private String url;
    private String baseUrl = "http://192.168.43.82:8080/api/";
    private Boolean hasPassWord = false, hasUserName = false;

    private UrlHandler(String url) {
        this.url = url;
    }

    private UrlHandler(String url, Boolean hasUserName, Boolean hasPassWord) {
        this.url = url;
        if (hasUserName) {
            this.hasUserName = hasUserName;
        }
        if (hasPassWord) {
            this.hasPassWord = hasPassWord;
        }
    }

    public String getUrl() {
        String url = baseUrl + this.url;
        if (hasUserName) {
            url = url + "/" + UserHandler.getInstance().getUserName();
        }
        if (hasPassWord) {
            url = url + "/" + UserHandler.getInstance().getPassWord();
        }
        return url;
    }
}
