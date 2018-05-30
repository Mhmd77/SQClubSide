package ir.sq.apps.sqclubside.controllers;

import android.graphics.Bitmap;

import java.util.List;

import ir.sq.apps.sqclubside.models.Club;
import ir.sq.apps.sqclubside.models.User;

/**
 * Created by Mohammad on 5/27/2018.
 */

public class UserHandler {
    private static final UserHandler ourInstance = new UserHandler();

    private User thisUser;

    private Club mClub;

    public static UserHandler getInstance() {
        return ourInstance;
    }

    private UserHandler() {
    }

    public void createClub(String name, String owner, String tele, String cell, String address) {
        mClub = new Club(thisUser.getUserName(), name, owner, tele, cell, address);
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

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        if (this.thisUser == null)
            this.thisUser = thisUser;
    }
}
