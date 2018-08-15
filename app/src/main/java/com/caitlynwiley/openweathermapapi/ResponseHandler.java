package com.caitlynwiley.openweathermapapi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import timber.log.Timber;

public class ResponseHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        /*switch (msg.what) {
            case DAILY_DATA_RESPONSE:
                Bundle bundle = msg.getData();
                // thought was to update UI here once data received
                break;
            case HOURLY_DATA_RESPONSE:

                break;
            default:
                Timber.e("Invalid message");
        }*/
    }
}
