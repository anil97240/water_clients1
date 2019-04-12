package com.allysoftsolutions.water_clients1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pl.droidsonroids.gif.GifTextView;

public class client_login extends AppCompatActivity {

    private static final String TAG = "Mohit";
    //user singin button
    Button btSignIn;
    TextView tv;
    GifTextView imgv1;
    ImageView i1;
    //user mobile number
    private EditText editTextMobile;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_client_login);
        mAuth = FirebaseAuth.getInstance();
        imgv1 = findViewById(R.id.cool);
        i1 = findViewById(R.id.imageView2);
        btSignIn = findViewById(R.id.btnlogin);
        editTextMobile = findViewById(R.id.edtmob);
        imgv1.setVisibility(View.VISIBLE);
        i1.setVisibility(View.GONE);
        editTextMobile.setVisibility(View.GONE);
        btSignIn.setVisibility(View.GONE);

        long timeInMillis = 1500;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                imgv1.setVisibility(View.INVISIBLE);
                i1.setVisibility(View.VISIBLE);
                editTextMobile.setVisibility(View.VISIBLE);
                btSignIn.setVisibility(View.VISIBLE);
            }
        }, timeInMillis);

        try {
            if (mAuth != null) {
                FirebaseUser user = mAuth.getCurrentUser();
                String provider = user.getProviders().get(0);
                if (provider.contains("password"))
                {

                }
                if (provider.contains("phone")) {
                    if (provider.contains("phone")) {
                        if (mAuth.getCurrentUser().getEmail().equals("admin@gmail.com")) {

                        } else {
                            Intent i = new Intent(getApplicationContext(), client_dashboard.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }

        btSignIn = findViewById(R.id.btnlogin);
        editTextMobile = findViewById(R.id.edtmob);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();
                if (mobile.isEmpty() || mobile.length() < 10) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                Intent intent = new Intent(client_login.this, client_mobile_verification.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                finish();
            }
        });
    }
}
