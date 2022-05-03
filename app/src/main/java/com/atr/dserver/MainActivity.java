package com.atr.dserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.bolyuk.DBolk;
import org.bolyuk.Kovalski;


public class MainActivity extends AppCompatActivity {
    public static Kovalski.Getter listener = new Kovalski.Getter() {
        @Override
        public void onLogAdded(String text) {
            textView.append("\n"+text);
        }
    };

    private static TextView textView;
    private static EditText input;
    private static ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        input = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.imageView);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            DBolk.init();
            Kovalski.addListener(3,listener);
            Kovalski.addListener(0,listener);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   DBolk.run(input.getText().toString());
                   input.setText("");
                }
            });
        }

    }
}