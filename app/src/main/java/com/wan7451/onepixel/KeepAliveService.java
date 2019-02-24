package com.wan7451.onepixel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class KeepAliveService extends Service {
    public KeepAliveService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ScreenListener l = new ScreenListener(this);
        l.begin(new ScreenListener.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                Log.e("KeepAlive", "onUserPresent");

            }

            @Override
            public void onScreenOn() {
                Log.e("KeepAlive", "onScreenOn");
                //销毁Activity
                LocalBroadcastManager instance = LocalBroadcastManager.getInstance(KeepAliveService.this);
                Intent intent = new Intent();
                intent.setAction("FinishActivity");
                instance.sendBroadcast(intent);
            }

            @Override
            public void onScreenOff() {
                Log.e("KeepAlive", "onScreenOff");
                //打开Activity
                KeepAliveActivity.start(KeepAliveService.this);
            }
        });
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
