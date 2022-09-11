package com.example.loginandregistrationapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginandregistrationapplication.ChiTietThueXe;
import com.example.loginandregistrationapplication.R;
import com.example.loginandregistrationapplication.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    LinearLayout lnNaptien, lnUserProfile, lnContact;
    ImageView logout, back;
    TextView nameAccount, soduAccount;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
        lnContact = findViewById(R.id.lnContact);
        ref = database.getReference("Users").child(auth.getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                nameAccount.setText(u.getHoten());
                soduAccount.setText(u.getSodu() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lnNaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NapTienActivity.class));
            }
        });

        lnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), UserProfileActivity.class));
            }
        });
        lnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ContactActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ChiTietThueXe.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xacNhan();
            }
        });
    }

    void initView() {
        lnNaptien = findViewById(R.id.ln_naptien);
        lnUserProfile = findViewById(R.id.ln_userprofile);
        logout = findViewById(R.id.btn_logout);
        nameAccount = findViewById(R.id.account_name);
        soduAccount = findViewById(R.id.acoount_sodu);
        back = findViewById(R.id.acc_back);
    }

    private void xacNhan() {
        //Use the context of current activity
        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setMessage("Bạn có chắc muốn đăng xuất không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(AccountActivity.this, MainActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//flags to clear your history
                startActivity(logoutIntent);
                dialogInterface.dismiss();
                finish();
                //dont forget to clear any user related data in your preferences
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}