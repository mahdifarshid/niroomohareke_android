package com.application.mahabad.niroomohareke.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class PermissionHandel {
    private Context context;
    private static final int WRITE_EXTERNAL_STORAGE = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    public PermissionHandel(Context context) {
        this.context = context;
    }

    public boolean is_havepermistion() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    public void checkPermision() {
        permissionStatus = context.getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("اجازه ی دسترسی به حافظه");
                builder.setMessage("این برنامه برای ذخیره ی pdf ها در حافظه داخلی نیاز به اجازه ی شما دارد");
                builder.setPositiveButton("اجازه دادن", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
                    }
                });
                builder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            editor.commit();
        } else {
            proceedAfterPermission();
        }
    }

    private void proceedAfterPermission() {
//        Toast.makeText(context, "We got the Microphone Permission", Toast.LENGTH_LONG).show();
    }
}