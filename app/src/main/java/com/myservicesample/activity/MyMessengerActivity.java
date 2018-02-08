package com.myservicesample.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myservicesample.R;
import com.myservicesample.services.MyMessengerService;

/**
 * Created by HP 240 G5 on 08-02-2018.
 */

public class MyMessengerActivity extends AppCompatActivity {
    private static EditText num1,num2;
    private static TextView txtResult;
    private static Button btnAdd,btnBindService,btnunBindService;
    boolean isBound = false;

    private Messenger mService = null;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mService = new Messenger(iBinder);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    private class IncomingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msgfromService) {
            super.handleMessage(msgfromService);
            switch (msgfromService.what)
            {
                case 100:
                    Bundle bundle = msgfromService.getData();
                    int results = bundle.getInt("results",0);
                    txtResult.setText(""+results);

                    break;

                    default:
                        super.handleMessage(msgfromService);
            }
        }
    }

    Messenger incomingMessenger = new Messenger(new IncomingHandler());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ipc);
        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);

        txtResult = (TextView)findViewById(R.id.txtResult);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBindService = (Button)findViewById(R.id.btnBindService);
        btnunBindService = (Button)findViewById(R.id.btnunBindService);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultStr = "";
                int numOne = Integer.parseInt(num1.getText().toString());
                int numTwo = Integer.parseInt(num2.getText().toString());

                Message msgToService = Message.obtain(null,12);
                Bundle bundle = new Bundle();
                bundle.putInt("numOne",numOne);
                bundle.putInt("numTwo",numTwo);
                msgToService.setData(bundle);
                msgToService.replyTo = incomingMessenger;
                try {
                    mService.send(msgToService);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyMessengerActivity.this, MyMessengerService.class);
                bindService(intent,connection,BIND_AUTO_CREATE);

            }
        });

        btnunBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound)
                {
                    unbindService(connection);
                    isBound=false;
                }
            }
        });
    }


}
