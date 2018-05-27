package ir.sq.apps.sqclubside.models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohammad on 5/27/2018.
 */

public class Club {
    //TODO Complete This Class
    private int type;

    private Double latitude = -43.0;
    private Double longtitude = -50.8;
    private Double rate;

    private String name;
    private String ownerUserName = "aliali";
    private String owner;
    private String telePhoneNumber;
    private String cellPhoneNumber;
    private String address;
    private String openTime;
    private String price;
    private String closeTime;

    private List<Bitmap> images;
    private List<String> tags;

    public Club(String name, String owner, String telePhoneNumber, String cellPhoneNumber, String adress) {
        this.name = name;
        this.owner = owner;
        this.telePhoneNumber = telePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.address = adress;
    }

    public void addImages(List<Bitmap> images) {
        this.images.addAll(images);
    }

    public void addTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("owner", owner);
            object.put("telePhoneNumber", telePhoneNumber);
            object.put("cellPhoneNumber", cellPhoneNumber);
            object.put("address", address);
            object.put("latitude", latitude);
            object.put("longtitude", longtitude);
            object.put("ownerUserName", ownerUserName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
