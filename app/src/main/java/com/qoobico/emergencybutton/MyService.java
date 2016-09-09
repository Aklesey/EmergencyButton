package com.qoobico.emergencybutton;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.qoobico.emergencybutton.fragment.AddContactActivity;


public class MyService extends Service {

    final String LOG_TAG = "myLogs";
    int screen_status = 0;
    long screen_start_time = 0;
    long screen_max_time = 1250;  //Максимальное время между нажатиями

    BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_ON)) {

                if (screen_status == 0) {
                    screen_start_time = System.currentTimeMillis();
                }

                screen_status++;  //Счётчик нажатий

                someTask();

            }
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                if (screen_status == 0) {
                    screen_start_time = System.currentTimeMillis();
                }

                screen_status++;

                someTask();

            }
        }
    };


    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenReceiver, intentFilter);

        someTask();

        return super.onStartCommand(intent, flags, START_STICKY);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    public void someTask() {
        if ((System.currentTimeMillis() - screen_start_time) > (screen_max_time * screen_status)) {
            screen_status = 0;
        }
        Log.i(LOG_TAG, "shoto");
        if (screen_status == 3) {
            task();
        }
    }

    protected void task() {

        String[] numbers = AddContactActivity.getDataPhoneList();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CallReceiver callReceiver = new CallReceiver();
        callReceiver.onReceive(this, callIntent);

        EndCallListener callListener = new EndCallListener();
        TelephonyManager mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
        for (int i = 0; i < numbers.length; ) {
            if (!EndCallListener.offHook) {
                try {
                    callIntent.setData(Uri.parse("tel:" + numbers[i]));
                    vibroAlarm();
                    startActivity(callIntent);
                    i++;
                } catch (Exception e) {
                    Log.i(LOG_TAG, e.getMessage());
                }
                if (EndCallListener.offHook) {
                    i++;
                }
            }
            mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    private void vibroAlarm() {
        long mills = 1000L;
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(mills);
    }
}


class EndCallListener extends PhoneStateListener {
    final String LOG_TAG = "myLogs";
    static boolean offHook;

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        if (TelephonyManager.CALL_STATE_RINGING == state) {

            Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
        }
        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            offHook = true;            //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
            Log.i(LOG_TAG, "OFFHOOK");
        }
        if (TelephonyManager.CALL_STATE_IDLE == state) {
            offHook = false;
            //when this state occurs, and your flag is set, restart your app
            Log.i(LOG_TAG, "IDLE");
        }
    }
}
