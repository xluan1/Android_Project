package com.xuanluan.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.xuanluan.myapplication.MainActivity;
import com.xuanluan.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    EditText email,password;
    TextView signUp;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        //click dong chu Dang ky
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
        //click nut Dang nhap
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginUser() {
        String userEmail= email.getText().toString();
        String userPassword= password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email không được để trống!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Mật khẩu không được để trống!",Toast.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công!",Toast.LENGTH_LONG).show();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Đăng nhập thất bại!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void AnhXa(){
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pgb_1);
        progressBar.setVisibility(View.GONE);
        signIn = findViewById(R.id.btn_dangnhap);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_matkhau);
        signUp = findViewById(R.id.txt_signup);
    }
}