package jp.tomiyama.noir.countdownservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainService extends Service {

    private static final String TAG = "MainService";
    private Timer mTimer = null;
    private int mCount = 60;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand():");
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mCount--;
                // MainActivityへデータを送信
                sendBroadcast(String.valueOf(mCount));
            }
        }, 0, 1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy():");
        // タイマーを停止
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind():");
        return null;
    }

    private void sendBroadcast(String message) {
        Log.d(TAG, "sendBroadcast: " + message);

        // IntentをブロードキャストすることでMainActivityへデータを送信
        Intent intent = new Intent();
        intent.setAction("ACTION");
        intent.putExtra("message", message);
        getApplicationContext().sendBroadcast(intent);
    }
}
