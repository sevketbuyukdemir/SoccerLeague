package com.sevketbuyukdemir.soccerleague.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.annotation.Nullable;

import static com.sevketbuyukdemir.soccerleague.utils.GlobalVariables.INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION;

/**
 * Added for internet connection control in app we trigger this service in MainActivity and
 * FixtureActivity
 */
public class InternetConnControlService extends Service {
    /**
     * Handler for message sending to netControlRunnable
     */
    Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Handler post to our controller runnable.
     * @param intent Intent
     * @param flags int
     * @param startId int
     * @return constant from android.app.Service class
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(netControlRunnable);
        return START_STICKY;
    }

    /**
     * Network controller function with ConnectivityManager
     * if network state is success return true,
     * when fail return false.
     * @param context Context
     * @return boolean
     */
    public boolean isNetworkON(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * This runnable control network every millisecond and send broadcast about network state.
     * We catch this broadcast from MainActivity and FixtureActivity.
     * Look at this activity for detailed information about BroadcastReceivers
     */
    private Runnable netControlRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(netControlRunnable, SystemClock.elapsedRealtime()%1000);
            Intent bcI = new Intent();
            bcI.setAction(INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION);
            bcI.putExtra("network_status", ""+isNetworkON(InternetConnControlService.this));
            sendBroadcast(bcI);
        }
    };
}
