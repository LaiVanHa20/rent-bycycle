package com.example.loginandregistrationapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginandregistrationapplication.R;
import com.example.loginandregistrationapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName, phoneNumber, password, email, repassword;
    Button btnRegister;
    ImageView btnBack;
    TextView alreadyGaveAccount;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyGaveAccount = findViewById(R.id.aready_gave_account);

        fullName = findViewById(R.id.full_name);
        phoneNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        btnBack = findViewById(R.id.ivBack);
        repassword = findViewById(R.id.repassword);

        btnRegister = findViewById(R.id.btn_register);
        progressDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        alreadyGaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String ipEmail = email.getText().toString();
        String ipFullName = fullName.getText().toString();
        String ipPhoneNumber = phoneNumber.getText().toString();
        String ipPassword = password.getText().toString();
        String ipRepassword = repassword.getText().toString();

        if(!ipEmail.matches(emailPattern)){
            email.setError("Nhập email chính xác");
        }
        else if(ipPassword.isEmpty() || ipPassword.length()<6){
            password.setError("Nhập mật khẩu thích hợp");
        }
        else if(!ipPassword.equals(ipRepassword)){
            repassword.setError("Mật khẩu hai trường không khớp");
        }
        else{
            progressDialog.setMessage("Vui lòng chờ trong khi đăng ký");
            progressDialog.setTitle("Đăng ký");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(ipEmail,ipPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        String hoten = fullName.getText().toString();
                        String Email = email.getText().toString();
                        String sodt = phoneNumber.getText().toString();
                        String matkhau = password.getText().toString();
                        String uid = mAuth.getCurrentUser().getUid();
                        User u = new User(uid, hoten, Email, sodt, matkhau);
                        saveUser(u);
                        //Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    void saveUser(User u) {
        ref = database.getReference().child("Users").child(u.getUid());
        ref.setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() { // bat trang thai luu tru
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Dang ki thanh cong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}