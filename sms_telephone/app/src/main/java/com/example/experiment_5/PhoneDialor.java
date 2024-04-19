package com.example.experiment_5;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneDialor extends AppCompatActivity {

    EditText phoneNumber;
    Button makeCall;
    TextView openSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialor);

        phoneNumber = findViewById(R.id.callPhoneNumber);
        makeCall = findViewById(R.id.makeCall);
        openSms = findViewById(R.id.openSms);

        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PhoneDialor.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PhoneDialor.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                } else {
                    String phoneNo = phoneNumber.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNo));
                    startActivity(intent);
                }
            }
        });

        openSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneDialor.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}