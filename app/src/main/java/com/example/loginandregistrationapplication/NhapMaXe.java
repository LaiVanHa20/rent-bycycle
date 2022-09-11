package com.example.loginandregistrationapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginandregistrationapplication.activity.AccountActivity;
import com.example.loginandregistrationapplication.activity.HomeActivity;
import com.example.loginandregistrationapplication.models.TramXe;
import com.example.loginandregistrationapplication.models.Xe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NhapMaXe extends AppCompatActivity {
    Button mokhoa;
    EditText nhapmaxe;
    LinearLayout lnQuetma;
    FirebaseDatabase database;
    DatabaseReference ref;
    ImageView back, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_ma_xe);
        initView();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Xe");
        mokhoa = findViewById(R.id.btn_mokhoa);
        back = findViewById(R.id.back);
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), AccountActivity.class)));
        back.setOnClickListener(view -> {
            pressBack();
        });
        mokhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode(nhapmaxe.getText().toString());
            }
        });

        lnQuetma = findViewById(R.id.lnquetma);
        lnQuetma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), QuetMaXe.class));
            }
        });
    }

    void checkCode(String code) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean res = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Xe t = dataSnapshot.getValue(Xe.class);
                    if(t.getMaxe().equals(code)) {
                        // hien thi dialog
                        res = true;
                        showTrueCustomDialog(Gravity.CENTER, R.layout.dialog_layout, code);
                        break;
                    }
                }
                if(res == false) {
                    showFalseCustomDialog(Gravity.CENTER);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }
        );
    }

    void showTrueCustomDialog(int gravity, int layout, String code) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams atr  = window.getAttributes();
        atr.gravity = gravity;
        window.setAttributes(atr);

        if(gravity == Gravity.CENTER) {
            dialog.setCancelable(true);
        } else dialog.setCancelable(false);

        Button huy, dongy;
        huy = dialog.findViewById(R.id.btn_huy);
        dongy = dialog.findViewById(R.id.btn_dongy);
        TextView maxe = dialog.findViewById(R.id.tv_maxe);
        maxe.setText(code);

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ChiTietThueXe.class));
            }
        });

        dialog.show();
    }
    void showFalseCustomDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.nhapsai_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams atr  = window.getAttributes();
        atr.gravity = gravity;
        window.setAttributes(atr);

        if(gravity == Gravity.CENTER) {
            dialog.setCancelable(true);
        } else dialog.setCancelable(false);

        Button nhaplai = dialog.findViewById(R.id.btn_nhaplai);
        TextView thongbao = dialog.findViewById(R.id.tv_thongbao);
        thongbao.setText("ma xe khong hop le");

        dialog.show();
        nhaplai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void pressBack(){
        Intent intent = new Intent(NhapMaXe.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    void initView() {
        nhapmaxe = findViewById(R.id.edt_nhapmaxe);
    }
}