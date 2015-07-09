package com.example.getinstalledapks;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private PackageManager packageManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();
        List<PackageInfo> mAllPackages = new ArrayList<PackageInfo>();
        mAllPackages = packageManager.getInstalledPackages(0);

        for (int i = 0; i < mAllPackages.size(); i++) {
            PackageInfo packageInfo = mAllPackages.get(i);
            Log.i("package path", "" + packageInfo.applicationInfo.sourceDir);
            Log.i("apk name", "" + packageInfo.applicationInfo.loadLabel(packageManager));
        }
    }

}
