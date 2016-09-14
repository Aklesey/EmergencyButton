package com.qoobico.emergencybutton;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.qoobico.emergencybutton.Gps.GPSTracker;
import com.qoobico.emergencybutton.adapter.MailSenderClass;
import com.qoobico.emergencybutton.fragment.AddContactActivity;


public class MyServiceButton extends Service {

    static int anInt = 0;
    final String LOG_TAG = "myLogs";
    int screen_status = 0;
    long screen_start_time = 0;
    long screen_max_time = 1250;  //Максимальное время между нажатиями
    private double lat;
    private double lon;
    Context mainContext;


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
        Log.d(LOG_TAG, "onCreateasdasdasdas");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mainContext = this;
        Log.d(LOG_TAG, "onStartCommandasdasdasdas");
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
        Log.d(LOG_TAG, "onDestroyasdasdasdas");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBindadasdasdasd");
        return null;
    }

    public void someTask() {
        if ((System.currentTimeMillis() - screen_start_time) > (screen_max_time * screen_status)) {
            screen_status = 0;
        }
        Log.i(LOG_TAG, "shotoqqqqqqdadsadas");


        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000L);

        listener();


    }

    protected void task() {
        String[] numbers = AddContactActivity.getDataPhoneList();
        emails = AddContactActivity.getDataEmailList();
        sender_mail_async async_sending = new sender_mail_async();
        async_sending.execute();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (anInt < numbers.length) {
            sendSMS(numbers[anInt]);
            callIntent.setData(Uri.parse("tel:" + numbers[anInt]));
            try {
                startActivity(callIntent);
            } catch (Exception e) {
                Log.i(LOG_TAG, "qqqqqqqqqqqq___" + e);
            }
            anInt++;
        } else {
            anInt = 0;
            voider();
            stopService(new Intent(this, MyServiceButton.class));
            onDestroy();
        }
        Log.i(LOG_TAG, "__= 1111111111111111" + anInt);
    }

    String title = "SOS, ALARM, HELP ME";
    String text = "HELP ME!I'm in trouble";
    String from = "hiddensossender@gmail.com";
    String where;
    String attach = "";
    String[] emails;

    private void sendSMS(String number) {
        String message = "HELP ME!I'm in trouble";
        message += "\nhttp://maps.google.com/?q=" + lat + "," + lon;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
    }

    private class sender_mail_async extends AsyncTask<Object, String, Boolean> {
        ProgressDialog WaitingDialog;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }

        @Override
        protected Boolean doInBackground(Object... params) {


            MailSenderClass sender = new MailSenderClass(from, "nhfvdfq77");
            try {

                text += "\nhttp://maps.google.com/?q=" + lat + "," + lon;


                sender.sendMail(title, text, from, emails[anInt], attach);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sender.sendMail(title, text, from, emails[anInt], attach);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            return false;
        }
    }

    private void voider() {
        TelephonyManager mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(null, PhoneStateListener.LISTEN_NONE);
    }

    private void listener() {
        EndCallListenerButton callListener = new EndCallListenerButton(this);
        TelephonyManager mTM;
        mTM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}

class EndCallListenerButton extends PhoneStateListener {
    final String LOG_TAG = "myLogs";
    MyServiceButton myService;

    public EndCallListenerButton(MyServiceButton myService) {
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


