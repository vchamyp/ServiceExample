package com.myservicesample.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by HP 240 G5 on 08-02-2018.
 */

public class MyBoundService extends Service {


    private MyLocalBinder myLocalBinder = new MyLocalBinder();

    public class MyLocalBinder extends Binder
    {

        public MyBoundService getService()
        {
            return MyBoundService.this;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myLocalBinder;
    }

    public int add(int a,int b)
    {
        return a+b;
    }

    public int sub(int a,int b)
    {
        return a-b;
    }

    public int mul(int a,int b)
    {
        return a*b;
    }

    public float div(int a,int b)
    {
        return (float)a/(float)b;
    }
}
