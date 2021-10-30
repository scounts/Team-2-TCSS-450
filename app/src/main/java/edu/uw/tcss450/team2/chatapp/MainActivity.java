package edu.uw.tcss450.team2.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.v("ONSTART", "Custom v message");
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("ONRESUME", "Custom d message");
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.i("ONPAUSE", "Custom i message");
    }


    @Override
    protected void onStop() {
        super.onStop();

        Log.w("ONSTOP", "Custom w message");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("ONDESTROY", "Custom e message");
    }
}