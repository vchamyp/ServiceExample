package com.myservicesample.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;


/**
 * Created by HP 240 G5 on 08-02-2018.
 */

public class MyMessengerService extends Service {

    private class IncomingHandler extends Handler
    {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case 12:
                    Bundle bundle = msg.getData();
                    int numOne = bundle.getInt("numOne",0);
                    int numTwo = bundle.getInt("numTwo",0);
                    int results = addnumbers(numOne,numTwo);
                    Toast.makeText(getApplicationContext(),"The Result is "+results,Toast.LENGTH_LONG).show();

                    Messenger incomingMessenger = msg.replyTo;
                    Message msgToActivity = Message.obtain(null,100);
                    Bundle bundletoActivity = new Bundle();
                    bundletoActivity.putInt("results",results);
                    msgToActivity.setData(bundletoActivity);
                    try {
                        incomingMessenger.send(msgToActivity);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    Messenger messenger =new Messenger(new IncomingHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public int addnumbers(int a,int b)
    {
        return a+b;
    }
}
