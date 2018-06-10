package ir.sq.apps.sqclubside.models;

/**
 * Created by Mohammad on 5/30/2018.
 */

public class User {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    private String userName;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    private String passWord;
}
