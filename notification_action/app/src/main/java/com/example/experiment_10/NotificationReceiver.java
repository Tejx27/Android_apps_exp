package com.example.experiment_10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        if (action != null && action.equals("ACTION_1")) {
            Toast.makeText(context, "Action clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}