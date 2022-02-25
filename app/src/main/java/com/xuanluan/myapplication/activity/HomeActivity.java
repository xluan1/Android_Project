package com.xuanluan.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.xuanluan.myapplication.MainActivity;
import com.xuanluan.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    TextView login,register;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AnhXa();
        if(auth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
            }
        });

    }

    private void AnhXa(){
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pgb_1);
        progressBar.setVisibility(View.GONE);
        login = findViewById(R.id.txt_login);
        register = findViewById(R.id.txt_register);
    }
}