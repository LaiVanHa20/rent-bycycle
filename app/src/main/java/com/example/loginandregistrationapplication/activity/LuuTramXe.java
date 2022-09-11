//package com.example.loginandregistrationapplication.activity;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.loginandregistrationapplication.R;
//import com.example.loginandregistrationapplication.models.TramXe;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class LuuTramXe extends AppCompatActivity {
//    EditText edtmatram, edtdiachi;
//    Button save;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_luu_tram_xe);
//
//        edtmatram = findViewById(R.id.edt_matram);
//        edtdiachi = findViewById(R.id.edt_diachi);
//
//        save = findViewById(R.id.btn_save_tramxe);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String matram = edtmatram.getText().toString();
//                String diachi = edtdiachi.getText().toString();
//                TramXe tramXe = new TramXe(matram, diachi, 0);
//
//                ref = database.getReference().child("Tram Xe").child(matram);
//                ref.setValue(tramXe).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(getBaseContext(), "Them tram xe thanh cong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("tag", e.getMessage());
//                    }
//                });
//            }
//        });
//    }
//}