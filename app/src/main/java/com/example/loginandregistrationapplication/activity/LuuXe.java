//package com.example.loginandregistrationapplication.activity;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.loginandregistrationapplication.R;
//import com.example.loginandregistrationapplication.models.TramXe;
//import com.example.loginandregistrationapplication.models.Xe;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class LuuXe extends AppCompatActivity {
//
//    EditText matram, maxe, gia;
//    Button save;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_luu_xe);
//
//        matram = findViewById(R.id.edt_matram2);
//        maxe = findViewById(R.id.edt_maxe);
//        gia = findViewById(R.id.edt_giaxe);
//
//        save = findViewById(R.id.btn_save_xe);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String Maxe = maxe.getText().toString();
//                Xe xe = new Xe(Maxe);
//
//                ref = database.getReference().child("Xe").push();
//                ref.setValue(xe).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(getBaseContext(), "Them xe thanh cong", Toast.LENGTH_SHORT).show();
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