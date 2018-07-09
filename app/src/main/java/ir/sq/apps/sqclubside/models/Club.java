package ir.sq.apps.sqclubside.models;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.sq.apps.sqclubside.utils.Constants;

/**
 * Created by Mohammad on 5/27/2018.
 */

public class Club {
    //TODO Complete This Class
    private int type = Constants.TYPE_NONE;

    private Double latitude = -43.0;
    private Double longtitude = -50.8;
//    private Double rate;

    private String name;
    private String ownerUserName;
    private String owner;
    private String telePhoneNumber;
    private String cellPhoneNumber;
    private String address;
//    private String openTime;
//    private String price;
//    private String closeTime;

    private List<Bitmap> images;
    private List<String> tags;
    private List<Plan> plans;
    private List<String> nameImages;

    public Club(String ownerUserName, String name, String owner, String telePhoneNumber, String cellPhoneNumber, String address) {
        this.name = name;
        this.owner = owner;
        this.telePhoneNumber = telePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.address = address;
        this.ownerUserName = ownerUserName;
        images = new ArrayList<>();
        tags = new ArrayList<>();
        nameImages = new ArrayList<>();
        plans = new ArrayList<>();
    }
    public List<String> getNameImages() {
        return nameImages;
    }

    public void addImages(List<Bitmap> images) {
        this.images.addAll(images);
    }

    public void addTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public JSONObject toJson() {
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
            JSONArray jsonArray = new JSONArray();
            for (String tag : tags) {
                jsonArray.put(tag);
            }
            if (tags.size() > 0) {
                object.put("tags", jsonArray);
            }
            if (type != Constants.TYPE_NONE) {
                object.put("type", type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void addTags(String tag) {
        this.tags.add(tag);
    }

    public void addImage(Bitmap bitmap) {
        images.add(bitmap);
    }


    public String getImageName(int pos) {
        return nameImages.get(pos);
    }

    public int getType() {
        return type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public String getName() {
        return name;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public String getOwner() {
        return owner;
    }

    public String getTelePhoneNumber() {
        return telePhoneNumber;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Bitmap> getImages() {
        return images;
    }

    public List<String> getTags() {
        return tags;
    }
    public void addNameImage(String img) {
        nameImages.add(img);
    }

    public void addPlan(Plan plan) {
        plans.add(plan);
    }

    public List<Plan> getPlans() {
        return plans;
    }
}
