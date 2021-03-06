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
import com.google.firebase.database.FirebaseDatabase;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.model.User;

public class RegistrationActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password,address,phoneNumber;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AnhXa();
        //click nut Dang ky
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
        //click dong chu Dang nhap
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createUser() {
        String userName= name.getText().toString();
        String userEmail= email.getText().toString();
        String userPassword= password.getText().toString();
        String userPhoneNumber= phoneNumber.getText().toString();
        String userAddress= address.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"T??n kh??ng ???????c ????? tr???ng!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email kh??ng ???????c ????? tr???ng!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"M???t kh???u kh??ng ???????c ????? tr???ng!",Toast.LENGTH_LONG).show();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(this,"M???t kh???u ph???i nhi???u h??n 6 k?? t???!",Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User(userName,userEmail,userPassword,userPhoneNumber,userAddress);
                            String id= task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this,"????ng k?? th??nh c??ng!",Toast.LENGTH_LONG).show();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this,"????ng k?? kh??ng th??nh c??ng!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void AnhXa(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressBar = findViewById(R.id.pgb_1);
        progressBar.setVisibility(View.GONE);
        signUp = findViewById(R.id.btn_dangky);
        name = findViewById(R.id.edt_ten);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_matkhau);
        address = findViewById(R.id.edt_diachi);
        phoneNumber = findViewById(R.id.edt_sdt);
        signIn = findViewById(R.id.txt_signin);
    }
}