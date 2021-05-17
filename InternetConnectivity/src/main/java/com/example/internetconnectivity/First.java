package com.example.internetconnectivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class First extends AppCompatActivity {

    static TextView tv_check_connection;
    public static int count;
    private BroadcastReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        count=0;
        tv_check_connection=findViewById(R.id.tv_check_connection);
        mNetworkReceiver = new NetworkChangeReceiver();
    }

    public static void dialog1(boolean value){

        if(value && count==1){
            count=0;
//            tv_check_connection.startAnimation(animation1);
            tv_check_connection.setVisibility(TextView.VISIBLE);
            tv_check_connection.setText("We are back !!!");
            tv_check_connection.setBackgroundColor(Color.parseColor("#0cc54e"));
            tv_check_connection.setTextColor(Color.WHITE);


            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
//                    tv_check_connection.setAnimation(animation2);
                    slideDown(tv_check_connection);
                    tv_check_connection.setVisibility(TextView.GONE);

                }
            };
            handler.postDelayed(runnable, 2000);
        }if(!value && count==0) {
            count=1;
            tv_check_connection.setVisibility(TextView.VISIBLE);
            slideUp(tv_check_connection);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.parseColor("#f2232c"));
            tv_check_connection.setTextColor(Color.WHITE);

//            tv_check_connection.setAnimation(animation1);
        }
    }

    private void registerNetworkBroadcastForNougat() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

        // registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterNetworkChanges();
    }

    public static void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    public static void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
}