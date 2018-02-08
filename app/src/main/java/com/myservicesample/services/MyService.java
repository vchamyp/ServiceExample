package com.myservicesample.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HP 240 G5 on 07-02-2018.
 */

public class MyService extends Service {


    private static final String TAG = MyService.class.getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG," onBind "+Thread.currentThread().getName());
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG," onCreate "+Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(TAG," onStartCommand "+Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleeptime",1);

        /*try {
            Thread.sleep(sleepTime*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        new MyAsyncTask().execute(sleepTime);

        return START_STICKY;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG," onDestroy "+Thread.currentThread().getName());
    }


    class MyAsyncTask extends AsyncTask<Integer,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e(TAG," onPreExecute "+Thread.currentThread().getName());
        }

        @Override
        protected String doInBackground(Integer... params) {

            Log.e(TAG," doInBackground "+Thread.currentThread().getName());

            int sleeptime = params[0];
            int ctrl = 1;
            //Long Operation
            while (ctrl<=sleeptime) {
                publishProgress("Counter is now "+ctrl);
                try {
                    Thread.sleep(sleeptime * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctrl++;
            }
            return "Total Count is(Service) "+ctrl;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.e(TAG," onProgressUpdate "+values[0]);

        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            Log.e(TAG," onPostExecute "+Thread.currentThread().getName());
            Intent intent = new Intent("action.service.to.activity");
            intent.putExtra("MyServiceData",str);
            sendBroadcast(intent);

            //stopSelf();
        }
    }
}
