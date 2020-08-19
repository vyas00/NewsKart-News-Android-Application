package com.example.android.newskart;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompletedReceiver";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,action + " received");
        if(action != null) {
            if (action.equals(Intent.ACTION_BOOT_COMPLETED) ) {
                Log.d(TAG, "onReceive: entered");
                 JobUtility.scheduleNewsJob(context);
            }

        }
    }
}