package com.example.loginandregistrationapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginandregistrationapplication.R;
import com.example.loginandregistrationapplication.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    ImageButton btnBack;
    TextView updateProfile,fullName,sdt,DOB,email;
    ImageView avatar;
    private static final String USERS = "Users";
    private String emailLogin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        auth = FirebaseAuth.getInstance();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

        btnBack = findViewById(R.id.ivBack);
        updateProfile = findViewById(R.id.txtUpdateProfile);
        fullName = findViewById(R.id.txtFullName);
        sdt = findViewById(R.id.txtPhone);
        DOB = findViewById(R.id.txtDOB);
        email = findViewById(R.id.txtEmail);
        avatar = findViewById(R.id.imgAvatar);
        getLoggedUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

    }
    void getLoggedUser() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child(auth.getCurrentUser().getUid()) ;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fullName.setText(user.getHoten());
                sdt.setText(user.getSodt());
                email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}