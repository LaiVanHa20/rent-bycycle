package com.example.loginandregistrationapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginandregistrationapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class NapTienActivity extends AppCompatActivity {
    TextView sodu;
    Button naptien;
    TextView t1, t2, t3, t4, t5, t6;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;
    float sotien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_tien);
        initView();

        // lay ra so du hien tai tren db

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child(auth.getCurrentUser().getUid());
        ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sotien = snapshot.getValue(Float.class);
                sodu.setText(sotien + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sotien = snapshot.getValue(Float.class);
                        sotien += 100000;
                        //DecimalFormat df = new DecimalFormat("#.000");
                        sodu.setText(sotien + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sotien = snapshot.getValue(Float.class);
                        sotien += 200000;
                        //DecimalFormat df = new DecimalFormat("#.000");
                        sodu.setText(sotien + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sotien = snapshot.getValue(Float.class);
                        sotien += 300000;
                        //DecimalFormat df = new DecimalFormat("#.000");
                        sodu.setText(sotien + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sotien = snapshot.getValue(Float.class);
                        sotien += 500000;
                        //DecimalFormat df = new DecimalFormat("#.000");
                        sodu.setText(sotien + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sotien = snapshot.getValue(Float.class);
                        sotien += 1000000;
                        //DecimalFormat df = new DecimalFormat("#.000");
                        sodu.setText(sotien + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sotien = snapshot.getValue(Float.class);
                        sotien += 2000000;
                        //DecimalFormat df = new DecimalFormat("#.000");
                        sodu.setText(sotien + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // luu tien vao db
                ref.child("sodu").setValue(Float.valueOf(sodu.getText().toString()));
            }
        });
    }

    void initView() {
        sodu = findViewById(R.id.tv_sodu);
        naptien = findViewById(R.id.btn_naptien);
        t1 = findViewById(R.id.tv_100);
        t2 = findViewById(R.id.tv_200);
        t3 = findViewById(R.id.tv_300);
        t4 = findViewById(R.id.tv_500);
        t5 = findViewById(R.id.tv_mottrieu);
        t6 = findViewById(R.id.tv_haitrieu);
    }
}