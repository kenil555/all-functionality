package com.ep.ai.hd.live.wallpaper.utils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {
    Activity activity;
    ActivityResultLauncher<String[]> permissionresultLauncher;

    public PermissionUtils(Activity activity, ActivityResultLauncher<String[]> permissionresultLauncher) {
        this.activity = activity;
        this.permissionresultLauncher = permissionresultLauncher;
    }

    public void takeStorageAccesPermission() {
        String[] permission;
        if (Build.VERSION.SDK_INT >= 33) {
            permission = new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO};
        } else {
            permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        permissionresultLauncher.launch(permission);

    }

    public boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            int readImagePermission = ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_MEDIA_IMAGES);
            int readVideoPermission = ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_MEDIA_VIDEO);
            return (readImagePermission == PERMISSION_GRANTED && readVideoPermission == PERMISSION_GRANTED);
        }else {
            int readExteralStorage = ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE);
            int writeExternalStorage = ContextCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return (readExteralStorage == PERMISSION_GRANTED && writeExternalStorage == PERMISSION_GRANTED);
        }
    }

    public static String getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return "blocked";
            }
            return "denied";
        }
        return "granted";
    }
}
