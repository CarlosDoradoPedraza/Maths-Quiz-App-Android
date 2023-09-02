
package com.example.mathsapk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Button button;
    private SensorManager sensorManager;
    private Sensor orientationSensor;
    private MyBroadcastReceiver myBroadcastReceiver;
    private MediaPlayer mediaPlayer;

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                    Toast.makeText(context, "Device connected to power", Toast.LENGTH_SHORT).show();
                    // Increase brightness
                    setBrightness(1.0f);
                } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                    Toast.makeText(context, "Device disconnected from power", Toast.LENGTH_SHORT).show();
                    // Decrease brightness
                    setBrightness(0.5f);
                }
            }
        }
    }

    private void setBrightness(float brightness) {
        try {
            // Save the current brightness level
            int currentBrightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);

            // Store the value in SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("brightness", currentBrightness);
            editor.apply();

            // Set the brightness level
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.screenBrightness = brightness;
            getWindow().setAttributes(layoutParams);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBroadcastReceiver = new MyBroadcastReceiver();
        Button instructions = findViewById(R.id.instructions);
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });

        Button difficultybtn = findViewById(R.id.difficulty);
        difficultybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        // Initialize MediaPlayer and set the audio file
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Create an IntentFilter and add the desired actions for the broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        // Register the BroadcastReceiver
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        sensorManager.unregisterListener(this);

        // Unregister the BroadcastReceiver
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float azimuth = event.values[0];
            float pitch = event.values[1];
            float roll = event.values[2];

            // Handle the orientation values as needed
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    public void openActivity3() {
        Intent intent = new Intent(this, difficullty.class);
        startActivity(intent);
    }
    public void openActivity5() {
        Intent intent = new Intent(this, MainActivity5.class);
        startActivity(intent);
    }
}
