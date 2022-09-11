package com.example.loginandregistrationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.loginandregistrationapplication.activity.AccountActivity;
import com.example.loginandregistrationapplication.activity.HomeActivity;
import com.example.loginandregistrationapplication.activity.MainActivity;
import com.example.loginandregistrationapplication.activity.RegisterActivity;
import com.example.loginandregistrationapplication.models.TramXe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NhapMaTramXe extends AppCompatActivity {

    Button xacnhan;
    EditText nhapmatram;
    LinearLayout lnQuetma;
    FirebaseDatabase database;
    DatabaseReference ref;
    ImageView back, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_ma_tram_xe);
        initView();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Tram Xe");
        xacnhan = findViewById(R.id.btn_xacnhan);
        back = findViewById(R.id.back);
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), AccountActivity.class)));
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCustomDialog(Gravity.CENTER);
                checkCode(nhapmatram.getText().toString());
                // so luong xe tang len 1
            }
        });
        back.setOnClickListener(view -> {
            pressBack();
        });

        lnQuetma = findViewById(R.id.lnquetmatram);
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
                                                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                       TramXe t = dataSnapshot.getValue(TramXe.class);
                                                       if (t.getMatramxe().equals(code)) {
                                                           // hien thi dialog
                                                           res = true;
                                                           showOkCustomDialog(Gravity.CENTER, R.layout.dialog_layout_traxe, code);
                                                           break;
                                                       }
                                                   }
                                                   if (res == false) {
                                                       showFalseCustomDialog(Gravity.CENTER);
                                                   }
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError error) {

                                               }
                                           }
        );
    }

    void showOkCustomDialog(int gravity, int layout, String code) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams atr = window.getAttributes();
        atr.gravity = gravity;
        window.setAttributes(atr);

        if (gravity == Gravity.CENTER) {
            dialog.setCancelable(true);
        } else dialog.setCancelable(false);

        Button huy, dongy;

        huy = dialog.findViewById(R.id.btn_huy_traxe);
        dongy = dialog.findViewById(R.id.btn_dongy_traxe);
        TextView sotien = dialog.findViewById(R.id.tv_sotien);
        float tien = getIntent().getFloatExtra("tongtien2", 0);
        sotien.setText(tien + "");

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // giam tien trong tai khoan

                startActivity(new Intent(getBaseContext(), HomeActivity.class));
            }
        });

        dialog.show();
    }

    void showFalseCustomDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.nhap_sai_tramxe_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams atr = window.getAttributes();
        atr.gravity = gravity;
        window.setAttributes(atr);

        if (gravity == Gravity.CENTER) {
            dialog.setCancelable(true);
        } else dialog.setCancelable(false);

        Button nhaplai = dialog.findViewById(R.id.btn_nhaplai_tramxe);
        TextView thongbao = dialog.findViewById(R.id.tv_thongbao_tramxe);
        thongbao.setText("Mã trạm xe không tồn tại");

        nhaplai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void pressBack(){
        Intent intent = new Intent(NhapMaTramXe.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    void initView() {
        nhapmatram = findViewById(R.id.edt_nhapmatram);
    }
}