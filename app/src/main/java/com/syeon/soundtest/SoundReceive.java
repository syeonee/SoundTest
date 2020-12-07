package com.syeon.soundtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.syeon.soundtest.audio.calculators.AudioCalculator;
import com.syeon.soundtest.audio.core.Callback;
import com.syeon.soundtest.audio.core.Recorder;

public class SoundReceive extends AppCompatActivity {

    private Recorder recorder;
    private AudioCalculator audioCalculator;
    private Handler handler;

    private TextView textAmplitude;
    private TextView textDecibel;
    private TextView textFrequency;
    private Button start_btn;
    private Button stop_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_receive);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recorder = new Recorder(callback);
        audioCalculator = new AudioCalculator();
        handler = new Handler(Looper.getMainLooper());

        textAmplitude = (TextView) findViewById(R.id.textAmplitude);
        textDecibel = (TextView) findViewById(R.id.textDecibel);
        textFrequency = (TextView) findViewById(R.id.textFrequency);

        start_btn = (Button) findViewById(R.id.start);
        stop_btn = (Button) findViewById(R.id.stop);
    }



    private Callback callback = new Callback() {

        @Override
        public void onBufferAvailable(byte[] buffer) {
            audioCalculator.setBytes(buffer);
            int amplitude = audioCalculator.getAmplitude();
            double decibel = audioCalculator.getDecibel();
            double frequency = audioCalculator.getFrequency();
//            final String hz;

//            if (frequency > 15000){
//                hz = String.valueOf(frequency + " Hz");
//            }else{
//                hz = "Under 150000Hz";
//            }

            final String amp = String.valueOf(amplitude + " Amp");
            final String db = String.valueOf(decibel + " db");
            final String hz = String.valueOf(frequency + " Hz");

            handler.post(new Runnable() {
                @Override
                public void run() {
                    textAmplitude.setText(amp);
                    textDecibel.setText(db);
                    textFrequency.setText(hz);
                    Log.i("syeon","run");
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("syeon","before");
        recorder.start();
        Log.i("syeon","after");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("syeon","before1");
        recorder.stop();
        Log.i("syeon","after1");
    }
}