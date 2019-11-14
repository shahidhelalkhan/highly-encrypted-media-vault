package com.shahid.medialocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shahid.medialocker.utils.Constants;
import com.shahid.medialocker.utils.SharedPrefUtils;

public class NewUserActivity extends AppCompatActivity {

    EditText etNewPin;
    EditText etConfirmPin;

    LinearLayout registerLayout;
    private static final String TAG = "NewUserActivity";
    String pin;
    String confirmPin;
    Button loginBtn;
    Button regBtn;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        registerLayout = findViewById(R.id.registerView);

        if(getIntent().getBooleanExtra("isNew",false)){


            registerLayout.setVisibility(View.VISIBLE);

            Toast.makeText(this,"New User",Toast.LENGTH_LONG).show();
            etNewPin = findViewById(R.id.etNewPin);
            etConfirmPin = findViewById(R.id.etConfirmPin);
            etPass = findViewById(R.id.etPass);


            regBtn = findViewById(R.id.regBtn);
            regBtn.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    pin = etNewPin.getText().toString();
                    Toast.makeText(getApplicationContext(),pin +" "+pin.length(),Toast.LENGTH_LONG).show();
                    confirmPin = etConfirmPin.getText().toString();
                    if(!TextUtils.isEmpty(pin)&&!TextUtils.isEmpty(confirmPin)) {
                        if (pin.equals(confirmPin)) {
                            Log.d(TAG, "onEditorAction: " + pin + " confirm ==> " + confirmPin);
                            Log.d(TAG, "onEditorAction: ");
                            SharedPrefUtils.changeFirstTime(NewUserActivity.this, confirmPin);
                            Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                            intent.putExtra("isGranted", true);
                            intent.putExtra(Constants.MSG, "Your pin has been saved");
                            startActivity(intent);
                            finish();

                        }
                    }
                }
            });



        }
    }
}
