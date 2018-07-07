package ir.sq.apps.sqclubside.controllers;

/**
 * Created by Mohammad on 5/27/2018.
 */

public enum UrlHandler {

    signUpUserURL("users"),
    signInUserURL("users/login"),
    createUserURL("clubs", false, true),
    getTagsURL("clubs/tags"),
    getImageClubURL(""),
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
            url = url + "/" + UserHandler.getInstance().getThisUser().getUserName();
        }
        if (hasPassWord) {
            url = url + "/" + UserHandler.getInstance().getThisUser().getPassWord();
        }
        return url;
    }
}
