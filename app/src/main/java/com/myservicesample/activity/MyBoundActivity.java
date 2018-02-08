package com.myservicesample.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myservicesample.services.MyBoundService;
import com.myservicesample.R;

/**
 * Created by HP 240 G5 on 08-02-2018.
 */

public class MyBoundActivity extends AppCompatActivity {

    private static EditText num1,num2;
    private static TextView txtResult;
    boolean isBound = false;
    private MyBoundService myBoundService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            MyBoundService.MyLocalBinder myLocalBinder = (MyBoundService.MyLocalBinder) iBinder;
            myBoundService = myLocalBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);




    }

    public void onClickEvent(View view)
    {
        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);

        txtResult = (TextView)findViewById(R.id.txtResult);

        String resultStr = "";
        int numOne = Integer.parseInt(num1.getText().toString());
        int numTwo = Integer.parseInt(num2.getText().toString());

        if(isBound)
        {
            switch (view.getId())
            {
                case R.id.btnAdd:
                    resultStr = String.valueOf(myBoundService.add(numOne,numTwo));

                    break;
                case R.id.btnSub:

                    resultStr = String.valueOf(myBoundService.sub(numOne,numTwo));

                    break;
                case R.id.btnMul:
                    resultStr = String.valueOf(myBoundService.mul(numOne,numTwo));

                    break;
                case R.id.btnDiv:
                    resultStr = String.valueOf(myBoundService.div(numOne,numTwo));

                    break;

            }
            txtResult.setText(resultStr);

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MyBoundActivity.this,MyBoundService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound)
        {
            unbindService(connection);
            isBound=false;
        }
    }
}
