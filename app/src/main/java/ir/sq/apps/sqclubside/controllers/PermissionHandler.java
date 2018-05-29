package ir.sq.apps.sqclubside.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;


public class PermissionHandler {
    public static String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET};

    public static void grantAllPermissions(Activity activity) {
        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        for (String permission :
                permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED)
                notGrantedPermissions.add(permission);
        }
        if (notGrantedPermissions.size() > 0)
            ActivityCompat.requestPermissions(activity,
                    notGrantedPermissions.toArray(new String[notGrantedPermissions.size()]),
                    1);
    }

    private static String[] getPermissionsFromManifest(Activity activity) {
        String[] result = permissions;
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
            result = info.requestedPermissions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkPermissionGranted(String permission, Activity activity) {
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
