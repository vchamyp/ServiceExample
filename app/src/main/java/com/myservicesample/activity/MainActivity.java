package com.myservicesample.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myservicesample.services.MyIntentService;
import com.myservicesample.services.MyService;
import com.myservicesample.R;

public class MainActivity extends AppCompatActivity {

    private static TextView txtDispService,txtDispIntenService;
    private static Button btnStart,btnStopService,btnIntentService,btnBound,btnIPC;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        txtDispService = (TextView)findViewById(R.id.txtDispService);
        txtDispIntenService = (TextView)findViewById(R.id.txtDispIntenService);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnStopService = (Button)findViewById(R.id.btnStopService);
        btnIntentService = (Button)findViewById(R.id.btnIntentService);
        btnBound = (Button)findViewById(R.id.btnBound);
        btnIPC = (Button)findViewById(R.id.btnIPC);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,MyService.class);
                intent.putExtra("sleeptime",10);
                startService(intent);

            }
        });


        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,MyService.class);
                stopService(intent);


            }
        });


        btnIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ResultReceiver resultReceiver = new MyResultReceiver(null);

                Intent intent = new Intent(MainActivity.this,MyIntentService.class);
                intent.putExtra("sleeptime",10);
                intent.putExtra("receiver",resultReceiver);
                startService(intent);

            }
        });

        btnBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyBoundActivity.class);
                startActivity(intent);
            }
        });

        btnIPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyMessengerActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.service.to.activity");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String data = intent.getStringExtra("MyServiceData");
            txtDispService.setText(data);

        }
    };

    private class MyResultReceiver extends ResultReceiver
    {

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if(resultCode==200&&resultData!=null)
            {
                final String data = resultData.getString("MyIntentReceiver");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtDispIntenService.setText(data);
                    }
                });


            }
        }
    }
}
