package jp.tomiyama.noir.countdownservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnStart;
    private Button btnStop;

    private Intent serviceIntent;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);


        btnStart = findViewById(R.id.startButton);
        btnStop = findViewById(R.id.stopButton);

        // MainServiceからのIntentを受信
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if(bundle!=null) {
                    String message = bundle.getString("message");
                    Log.d(TAG, "Message from MainService: " + message);
                    // TextViewへ文字列をセット
                    textView.setText(message);
                }
            }
        };

        // "ACTION" Intentフィルターをセット
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("ACTION");
        registerReceiver(mReceiver, mIntentFilter);

        serviceIntent = new Intent(this, MainService.class);
    }

    private void setServiceUI(boolean startable) {
        btnStart.setEnabled(startable);
        btnStop.setEnabled(!startable);
    }

    public void onClick(View view){
        int id = view.getId();

        if(id == R.id.startButton){
            setServiceUI(false);
            startService(serviceIntent);
        }else if(id == R.id.stopButton){
            setServiceUI(true);
            stopService(serviceIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }
}
