package com.example.mathsapk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class difficullty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficullty);

        Button medium = (Button) findViewById(R.id.button8);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity3();
            }
        });
        Button hard = (Button) findViewById(R.id.button9);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity4();
            }
        });
        Button easy = (Button) findViewById(R.id.button3);
       easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity5();
            }
        });
        ImageButton returnb = (ImageButton) findViewById(R.id.returnbutton1);
        returnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity1();
            }
        });
    }
    public void openActivity3() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void openActivity4() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    public void openActivity5() {
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }
    public void openactivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
