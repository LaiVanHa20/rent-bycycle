package com.example.loginandregistrationapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginandregistrationapplication.R;
import com.example.loginandregistrationapplication.adapter.RecyclerViewAdapter;
import com.example.loginandregistrationapplication.models.TramXe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListTramXeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<TramXe> tramXeArrayList;
    Button ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tram_xe);

        recyclerView = findViewById(R.id.listTramXe);
        ivBack = findViewById(R.id.button_back);
        tramXeArrayList = new ArrayList<>();
        getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(tramXeArrayList,this);
        recyclerView.setAdapter(recyclerViewAdapter);


        ivBack.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
        });
    }
    private void getData(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Tram Xe");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (tramXeArrayList != null){
                    tramXeArrayList.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TramXe tramXe = dataSnapshot.getValue(TramXe.class);
                    tramXeArrayList.add(tramXe);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}