package com.qoobico.emergencybutton;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.qoobico.emergencybutton.Gps.GPSTracker;
import com.qoobico.emergencybutton.fragment.AddContactActivity;


public class MyService extends Service {

    static int anInt = 0;
    final String LOG_TAG = "myLogs";
    int screen_status = 0;
    long screen_start_time = 0;
    long screen_max_time = 1250;  //Максимальное время между нажатиями
    private double lat;
    private double lon;

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
        GPSTracker gps = new GPSTracker(this);

        // check if GPS location can get Location
        if (gps.canGetLocation()) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Log.d("Your Location", "latitude:" + gps.getLatitude()
                        + ", longitude: " + gps.getLongitude());

                lon = gps.getLongitude();
                lat = gps.getLatitude();
            }
        }

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

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000L);

            listener();
            sendSMS();

        }
    }

    protected void task() {
        String[] numbers = AddContactActivity.getDataPhoneList();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (anInt < numbers.length) {
            callIntent.setData(Uri.parse("tel:" + numbers[anInt]));
            try {
                startActivity(callIntent);
            } catch (Exception e) {
                Log.i(LOG_TAG, "___" + e);
            }
            anInt++;
        } else {
            anInt = 0;
            voider();
            stopService(new Intent(this, MyService.class));
            startService(new Intent(this, MyService.class));
            return;
        }
        Log.i(LOG_TAG, "__=" + anInt);
    }

    private void sendSMS() {
        String[] numbers = AddContactActivity.getDataPhoneList();
        for (int i = 0; i <numbers.length ; i++) {
            String message = "HELP ME!I'm in trouble";
            message += "\nhttp://maps.google.com/?q=" + lat + "," + lon;


            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numbers[i], null, message, null, null);
        }
    }

    private void voider() {
        TelephonyManager mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(null, PhoneStateListener.LISTEN_NONE);
    }

    private void listener() {
        EndCallListener callListener = new EndCallListener(this);
        TelephonyManager mTM;
        mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}


class EndCallListener extends PhoneStateListener {
    final String LOG_TAG = "myLogs";
    MyService myService;

    public EndCallListener(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            Log.i(LOG_TAG, "OFFHOOK");

        }
        if (TelephonyManager.CALL_STATE_IDLE == state) {
            Log.i(LOG_TAG, "IDLE");
            myService.task();
        }
    }
}
