package com.application.mahabad.niroomohareke.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.ConnectivityReceiver;
import com.application.mahabad.niroomohareke.R;

public class ActivityNoConnection extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_noconnection);
            findViewById(R.id.btn_tryagain).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ConnectivityReceiver.isOnline()){
                        Intent intent=new Intent(ActivityNoConnection.this,SplashScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(ActivityNoConnection.this, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            findViewById(R.id.internet_settings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
