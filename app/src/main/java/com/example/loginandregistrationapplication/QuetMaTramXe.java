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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.loginandregistrationapplication.activity.AccountActivity;
import com.example.loginandregistrationapplication.activity.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

public class QuetMaTramXe extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView data;
    LinearLayout lnNhapma;
    ImageView back, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quet_ma_tram_xe);
        scannerView = findViewById(R.id.scanner_view_tramxe);
        codeScanner = new CodeScanner(this, scannerView);
        lnNhapma = findViewById(R.id.lnNhapmaTram);
        back = findViewById(R.id.back);
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), AccountActivity.class)));
        back.setOnClickListener(view -> {
            pressBack();
        });

        lnNhapma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), com.example.loginandregistrationapplication.NhapMaTramXe.class);
                float sotien = getIntent().getFloatExtra("tongtien", 0);
                intent.putExtra("tongtien2", sotien);
                startActivity(intent);
            }
        });

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        float sotien = getIntent().getFloatExtra("tongtien", 0);
                        showCustomDialog(Gravity.CENTER, sotien + "");
                    }
                });
            }
        });

        scannerView.setOnClickListener(view -> codeScanner.startPreview());

    }

    void showCustomDialog(int gravity, String sotien) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);

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
        TextView tvmaxe;
        tvmaxe = dialog.findViewById(R.id.tv_maxe);
        tvmaxe.setText(sotien);

        huy = dialog.findViewById(R.id.btn_huy);
        dongy = dialog.findViewById(R.id.btn_dongy);

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tru tien trong tai khoan
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Users").child(auth.getCurrentUser().getUid());
                ref.child("sodu").addListenerForSingleValueEvent(new ValueEventListener() {
                    float soduhientai = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        soduhientai = snapshot.getValue(Float.class);
                        ref.child("sodu").setValue(soduhientai - Float.valueOf(sotien));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // tang so luong xe trong tram

                startActivity(new Intent(getBaseContext(), HomeActivity.class));
            }

        });


        dialog.show();
    }

    private void pressBack(){
        Intent intent = new Intent(QuetMaTramXe.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}