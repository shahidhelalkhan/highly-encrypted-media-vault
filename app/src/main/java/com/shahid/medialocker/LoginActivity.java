package com.shahid.medialocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shahid.medialocker.utils.Constants;
import com.shahid.medialocker.utils.SharedPrefUtils;

public class LoginActivity extends AppCompatActivity {
    Button button;
    EditText etPass;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkForAuth();
        button = findViewById(R.id.btnLogin);
        etPass = findViewById(R.id.etPass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = etPass.getText().toString();
                if(pin.equals(SharedPrefUtils.getFromPref(getApplicationContext(), Constants.PIN))){
                    Log.d(TAG, "onClick: matched");
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    private void checkForAuth() {
        SharedPrefUtils sharedPrefrencesUtils = new SharedPrefUtils();
        if(sharedPrefrencesUtils.isFirstTime(this)){
            Log.d(TAG, "onCreate: ");
            Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
            intent.putExtra("isNew",true);
            startActivity(intent);
            finish();
        }
    }
}
