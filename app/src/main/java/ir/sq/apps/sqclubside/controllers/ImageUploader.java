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

import ir.sq.apps.sqclubside.utils.Constants;

/**
 * Created by Mohammad on 5/28/2018.
 */

public class ImageUploader {
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
                                Log.i("RESPONSE", response.toString());
                            } else {
                                success[0] = false;
                                Log.e("RESPONSE", "Error In Response");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("RESPONSE", "Error In Response : " + e.getMessage());

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
}
