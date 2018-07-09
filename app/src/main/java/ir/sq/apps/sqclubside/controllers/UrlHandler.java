package ir.sq.apps.sqclubside.controllers;


public enum UrlHandler {

    signUpUserURL("owners/sign-up"),
    signInUserURL("owners/login"),
    createUserURL("clubs", false, true),
    getTagsURL("clubs/tags"),
    uploadeImageURL("images", true, true),
    getImageClubURL("");


    private String url;
    private String baseUrl = "http://192.168.43.191:8080/api/";
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
