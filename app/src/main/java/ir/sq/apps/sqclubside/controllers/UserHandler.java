package ir.sq.apps.sqclubside.controllers;

import android.graphics.Bitmap;

import java.util.List;

import ir.sq.apps.sqclubside.models.Club;

/**
 * Created by Mohammad on 5/27/2018.
 */

public class UserHandler {
    private static final UserHandler ourInstance = new UserHandler();


    private Club mClub;

    private String userName;
    private String passWord;

    public static UserHandler getInstance() {
        return ourInstance;
    }

    private UserHandler() {
    }

    public void createClub(String name, String owner, String tele, String cell, String address) {
        mClub = new Club(userName, name, owner, tele, cell, address);
    }

    public void setImages(List<Bitmap> images) {
        mClub.addImages(images);
    }

    public void setTags(List<String> tags) {
        mClub.addTags(tags);
    }

    //TODO Complete WeeklyPlan
    private void setPlan() {

    }

    public Club getmClub() {
        return mClub;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }
}
