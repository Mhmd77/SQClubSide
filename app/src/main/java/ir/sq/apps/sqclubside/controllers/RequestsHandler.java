package ir.sq.apps.sqclubside.controllers;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import ir.sq.apps.sqclubside.models.Club;
import ir.sq.apps.sqclubside.utils.Constants;

/**
 * Created by Mohammad on 5/28/2018.
 */

public class RequestsHandler {
    private static final String TAG = "Connection";


    public static void getTagsFrom(String url, final OnResponse onResponse) {
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == Constants.SUCCESSFUL_CONNECTION) {
                                onResponse.onTagsRecieved(response.getJSONArray("object"));
                                Log.i("TAGS", String.valueOf(response.getJSONArray("object")));
                            } else {
                                Log.e(TAG + " : TAG", "Error In Response " + status);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG + " : TAG", "Error In Parse");
                        }
                    }

                    @Override
                    public void onError(ANError e) {
                        e.printStackTrace();
                        Log.e(TAG, "Error In Response : " + e.getMessage());
                    }
                });
    }

    public static boolean uploadImageTo(File file, String url) {
        final boolean[] success = new boolean[1];
        AndroidNetworking.upload(url)
                .addMultipartFile("photo", file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        Log.i("OnProgress", Long.toString(bytesUploaded / totalBytes));
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = Integer.parseInt(response.getString("status"));
                            if (status == Constants.SUCCESSFUL_CONNECTION) {
                                success[0] = true;
                                Log.i(TAG, response.toString());
                            } else {
                                success[0] = false;
                                Log.e(TAG, "Error In Response");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error In Response : " + e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        success[0] = false;
                        Log.e("Error", "message : " + error.getErrorBody() + " code : " + error.getErrorCode());
                    }
                });
        return success[0];
    }

    public static void sendClubTo(Club club, String url) {
        AndroidNetworking.post(url)
                .addJSONObjectBody(club.toJson())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == Constants.SUCCESSFUL_CONNECTION) {
                                Log.i(TAG, "response : " + response.toString());
                            } else {
                                String pas = response.get("object").toString();
                                Log.e(TAG, "Error In Response " + status + pas);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error In Response : " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public static void updateClubTo(Club club, String url) {
        AndroidNetworking.put(url)
                .addJSONObjectBody(club.toJson())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == Constants.SUCCESSFUL_CONNECTION) {
                                Log.i(TAG, "response:" + response.toString());
                            } else {
                                Log.e(TAG, "Error In Response");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error In Response : " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
