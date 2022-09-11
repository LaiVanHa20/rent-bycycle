package com.example.loginandregistrationapplication;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.loginandregistrationapplication.activity.AccountActivity;
import com.example.loginandregistrationapplication.models.TramXe;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChiTietThueXe extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {
    ImageView profile; // nut an de chuye sang man hinh thong tin ca nhan
    LinearLayout lnTraxe; // an de tra xe
    TextView thoigiandi, sotienhientai;
    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    private ArrayList<TramXe> tramXeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thue_xe);
        initView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        progressDialog = new ProgressDialog(ChiTietThueXe.this);
        progressDialog.setMessage("Vui lòng đợi trong giây lát!");
        tramXeArrayList = new ArrayList<>();

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> startActivity(new Intent(getBaseContext(), AccountActivity.class)));

        lnTraxe = findViewById(R.id.lntraxe);
        lnTraxe.setOnClickListener(view -> {
            Intent intent = new Intent(ChiTietThueXe.this, QuetMaTramXe.class);
            intent.putExtra("tongtien", 5000 + sotienhientai(count));
            startActivity(intent);
        });

        demthoigian();
    }

    int count = 0;
    Timer T=new Timer();
    void demthoigian() {
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        count++;
                        thoigiandi.setText(showGoneTime(count));
                        sotienhientai.setText(String.valueOf(sotienhientai(count) + 5000));
                    }
                });
            }
        }, 1000, 1000);
    }

    float sotienhientai(int sec) {
        float phut = sec / 60;
        float tien = (phut / 60) * 10000;
        return tien;
    }

    String showGoneTime(int second) {
        int h = second / 3600;
        int m = (second - h * 3600) / 60;
        int s = second % 60;
        if(m == 0) {
            return s + " giây";
        } else if(m != 0) {
            return m + " phút " + s + " giây";
        } else return h + " giờ "+ m + " phút " + s + " giây";
    }

    float convertSecToHour(int sec) {
        float h = sec / 3600;
        return h;
    }

    float currentFee(float hour) {
        return hour * 10000;
    }

    void initView() {
        thoigiandi = findViewById(R.id.txt_thoigiandi);
        sotienhientai = findViewById(R.id.txt_sotiencantra);
    }

    private static final int REQUEST_CODE_GPS_PERMISSION = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //TODO: Get current location
            getCurrentLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_GPS_PERMISSION);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Ha Noi and move the camera
        LatLng hanoi = new LatLng(21.0169598, 105.8114176);
        mMap.addMarker(new MarkerOptions().position(hanoi).title("Marker in Ha Noi"));

        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Tram Xe");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (tramXeArrayList != null){
//                    tramXeArrayList.clear();
//                }
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    TramXe tramXe1 = new TramXe();
                    tramXe1 = dataSnapshot.getValue(TramXe.class);
                    Log.d(TAG, "onDataChange: "+tramXe1.getDiachi());
                    tramXeArrayList.add(tramXe1);
                }

                Log.d(TAG, "onMapReady: "+tramXeArrayList.size());

                for(TramXe tramXe : tramXeArrayList){
                    LatLng daihoc = new LatLng(tramXe.getLatitude(), tramXe.getLongitude());
                    mMap.addMarker((new MarkerOptions().position(daihoc).title(tramXe.getDiachi())));
                }

//                Log.d(TAG, "onDataChange: "+tramXeArrayList.size());
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChiTietThueXe.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setMinZoomPreference(13);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hanoi));
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            return;
                        }
                        LatLng currentLocation =
                                new LatLng(location.getLatitude(), location.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in current location"));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    }
                });
    }

    //kiểm tra quyền truy cập GPS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_GPS_PERMISSION:
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        //TODO: Action when permission denied
                    }
                }
                break;
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}