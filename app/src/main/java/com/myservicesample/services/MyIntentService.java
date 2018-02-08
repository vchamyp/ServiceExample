package com.myservicesample.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HP 240 G5 on 07-02-2018.
 */

public class MyIntentService extends IntentService {


    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("Worker Thread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG," onCreate thread name "+Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG," onHandleIntent thread name "+Thread.currentThread().getName());

        ResultReceiver resultreceiver = intent.getParcelableExtra("receiver");

        int sleepTime = intent.getIntExtra("sleeptime",1);

        int ctrl = 1;
        //Long Operation
        while (ctrl<=sleepTime) {
            Log.i(TAG,"Counter is now "+ctrl);

            try {
                Thread.sleep(sleepTime * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctrl++;
        }

        Bundle bundle = new Bundle();
        bundle.putString("MyIntentReceiver","Total Count is(IntentService) "+ctrl);

        resultreceiver.send(200,bundle);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG," onDestroy thread name "+Thread.currentThread().getName());
    }
}
