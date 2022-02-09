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

import com.atr.dserver.dclasses.DBolk;
import com.atr.dserver.dclasses.DLogger;


public class MainActivity extends AppCompatActivity {
    public static DLogger.DLogInterface listener = new DLogger.DLogInterface() {
        @Override
        public void onLogChanged() {
            textView.setText(DLogger.getLog());
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
        DLogger.addListener(listener);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            DBolk.init();
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