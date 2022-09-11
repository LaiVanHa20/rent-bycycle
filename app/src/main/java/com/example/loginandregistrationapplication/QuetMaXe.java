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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.loginandregistrationapplication.activity.AccountActivity;
import com.example.loginandregistrationapplication.activity.HomeActivity;
import com.google.zxing.Result;

public class QuetMaXe extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView data;
    LinearLayout lnNhapma;
    ImageView back, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quet_maxe_activity);
        scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);
        lnNhapma = findViewById(R.id.lnNhapma);
        back = findViewById(R.id.back);

        lnNhapma.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), NhapMaXe.class)));
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), AccountActivity.class)));
        back.setOnClickListener(view -> {
            pressBack();
        });
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCustomDialog(Gravity.CENTER, result.getText());
                    }
                });
            }
        });

        scannerView.setOnClickListener(view -> codeScanner.startPreview());

    }

    void showCustomDialog(int gravity, String maxe) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);

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
        TextView tvmaxe;
        tvmaxe = dialog.findViewById(R.id.tv_maxe);
        tvmaxe.setText(maxe);

        huy = dialog.findViewById(R.id.btn_huy);
        dongy = dialog.findViewById(R.id.btn_dongy);

        huy.setOnClickListener(view -> dialog.dismiss());
        dongy.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), ChiTietThueXe.class)));


        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    private void pressBack(){
        Intent intent = new Intent(QuetMaXe.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}