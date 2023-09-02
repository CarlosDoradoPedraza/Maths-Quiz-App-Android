package com.example.mathsapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        ImageButton returnb = (ImageButton) findViewById(R.id.returnbutton5);
        returnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivityc();
            }
        });
    }

    public void openactivityc() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}