package com.example.tast;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    TextView v, g;

    View divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        divider = findViewById(R.id.divider);

        v = (TextView) findViewById(R.id.visible);
        g = (TextView) findViewById(R.id.gone);

        v.setText(getVersion());
        g.setText(getVersionCode());

        v.setOnClickListener(this);
        g.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visible:
                divider.setVisibility(View.VISIBLE);
                break;
            case R.id.gone:
                divider.setVisibility(View.GONE);
                break;
        }
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo("com.meizu.voiceassistant", 0);
            String version = info.versionName;
            return "###版本号为 : " + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "####找不到";
        }
    }

    public String getVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo("com.meizu.voiceassistant", 0);
            return "###版本号为 : " + info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return "####找不到";
        }
    }
}
