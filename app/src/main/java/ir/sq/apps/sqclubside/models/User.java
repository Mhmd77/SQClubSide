package ir.sq.apps.sqclubside.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String userName;
    private String email;
    private String passWord;
    private boolean verified;
    private List<Receipt> receipts;

    public User(String name, String userName, String email, String passWord, boolean verified) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.verified = verified;
        this.passWord = passWord;
        this.receipts = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }
}
