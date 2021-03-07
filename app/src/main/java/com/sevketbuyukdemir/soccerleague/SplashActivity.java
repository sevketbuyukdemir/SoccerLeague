package com.sevketbuyukdemir.soccerleague;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * This activity is launch activity of application.
 * Show application name and icon.
 * In this activity request to user for permissions.
 * if user allow for requests, launch MainActivity, else don't do anything.
 */
public class SplashActivity extends AppCompatActivity {
    ImageView app_icon;
    TextView splash_text;

    private static final int MY_PERMISSIONS_ACCESS_NETWORK_STATE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //initialize
        app_icon = findViewById(R.id.app_icon);
        splash_text = findViewById(R.id.splash_text);
        //initialize
        // This components show app icon and name
        app_icon.setImageResource(R.drawable.app_icon);
        splash_text.setText(R.string.application_name);
        // Permissions
        // first check and request for permissions.
        if(!(ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)){
            requestAccessNetworkStatePermission();
        }
        // Trigger authorization control thread with start function
        new enterProgramme().start();
    }

    // Request permission function with using ActivityCompat
    private void requestAccessNetworkStatePermission(){
        ActivityCompat.requestPermissions(SplashActivity.this,new String[] {Manifest.permission.ACCESS_NETWORK_STATE}, MY_PERMISSIONS_ACCESS_NETWORK_STATE);
    }

    /**
     * This thread is recursive thread if user don't authorize app, can not use our app :D
     */
    private class enterProgramme extends Thread{
        @Override
        public void run() {
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }finally {
                if (!(ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)) {
                    requestAccessNetworkStatePermission();
                    new enterProgramme().start();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}