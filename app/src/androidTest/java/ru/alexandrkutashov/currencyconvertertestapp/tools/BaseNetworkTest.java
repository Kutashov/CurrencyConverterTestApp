package ru.alexandrkutashov.currencyconvertertestapp.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Alexandr on 18.07.2017.
 */

public class BaseNetworkTest {

    private BroadcastReceiver br;

    protected void registerNetworkReceiver(Context context) {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                NetworkInfo info = intent.getExtras()
                        .getParcelable("networkInfo");
                NetworkInfo.State state = info.getState();


                if (state == NetworkInfo.State.CONNECTED) {

                    synchronized (BaseNetworkTest.this) {
                        BaseNetworkTest.this.notify();
                    }
                }
            }
        };

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(br, intentFilter);
    }

    protected void waitForNetworkConnected() throws InterruptedException {
        synchronized (this) {
            wait();
        }
    }

    protected void unregisterNetworkReceiver(Context context) {
        context.unregisterReceiver(br);
    }

    protected void setWifiEnabled(Context context, boolean flag) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(flag);
        if (flag) {
            try {
                waitForNetworkConnected();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setWifiDisabled(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }
}
