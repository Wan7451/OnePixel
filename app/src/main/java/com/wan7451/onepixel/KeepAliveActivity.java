package com.wan7451.onepixel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class KeepAliveActivity extends AppCompatActivity {

    private FinishReceiver receiver;
    private LocalBroadcastManager manager;

    public static void start(Context context) {
        Intent i = new Intent(context, KeepAliveActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = 1;
        params.width = 1;
        params.x = 0;
        params.y = 0;
        window.setAttributes(params);

        receiver = new FinishReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("FinishActivity");
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, filter);

        Log.e("KeepAlive","onCreate");
    }



    @Override
    protected void onDestroy() {
        Log.e("KeepAlive","onDestroy");
        manager.unregisterReceiver(receiver);
        super.onDestroy();
    }

    private static class FinishReceiver extends BroadcastReceiver {

        private WeakReference<KeepAliveActivity> activity;


        public FinishReceiver(@NonNull KeepAliveActivity activity) {
            this.activity = new WeakReference<KeepAliveActivity>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            KeepAliveActivity activity = this.activity.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
