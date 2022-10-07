package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver mScanReceiver;
    TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHello = findViewById(R.id.txtHello);
        mScanReceiver = new MyReceiver("com.android.server.scannerservice.broadcast", "scannerdata");
        registerReceiver(mScanReceiver, new IntentFilter("com.android.server.scannerservice.broadcast"));
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScanReceiver);
    }

    private class MyReceiver extends BroadcastReceiver {
        final private String action, extra;

        public MyReceiver(String action, String extra) {
            this.action = action;
            this.extra = extra;
            Log.e("receiver", "created");
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(this.action)) {
                //获取扫描结果
                final String scanResult = intent.getStringExtra(this.extra);
//                final String scanResult = new String(intent.getByteArrayExtra(this.extra));
                Log.e("scanresult", scanResult);
                txtHello.setText(scanResult);

            }
        }


    }
}