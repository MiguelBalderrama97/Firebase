package com.example.miguel.prototipo.Activities.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.prototipo.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecurityZoneActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("usuarios").child(MainActivity.dueño);
    GoogleMap mMap;

    private View mapView;
    private Button btnSetSecurityZone;

    private final float DEFAULT_ZOOM = 10;
    private int FAILED_CODE = 51;

    double secLat=0, secLon=0;
    int secRadius=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_zone);
        btnSetSecurityZone = findViewById(R.id.btn_find);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapView = mapFragment.getView();



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if(mapView != null && mapView.findViewById(Integer.parseInt("1")) != null){
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM,RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,40,180);
        }
    }

    public void clickDialogoPropio(View v){
        final Dialog dlgMiDialogo;
        dlgMiDialogo = new Dialog(this);
        //El Layuot, tenemos que decir como se ve
        dlgMiDialogo.setContentView(R.layout.cuadro_dialogo_security_zone);
        TextView txtVwTitu;
        final EditText edtTxtCaptu;
        Button btnOK;

        //Queremos que jale las ID de el layout cuadro de dialogo
        txtVwTitu = dlgMiDialogo.findViewById(R.id.txtVwTitu);
        edtTxtCaptu = dlgMiDialogo.findViewById(R.id.edtTxtCaptu);
        btnOK = dlgMiDialogo.findViewById(R.id.btnOK);

        txtVwTitu.setText("DEFINE SECURITY ZONE");
        edtTxtCaptu.setHint("Define radius in meters..");
        btnOK.setText("OK");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currentMarkerLocation = mMap.getCameraPosition().target;

                secLat = currentMarkerLocation.latitude;
                secLon = currentMarkerLocation.longitude;
                secRadius = Integer.parseInt(edtTxtCaptu.getText().toString());

                reference.child("radio").setValue(secRadius);
                reference.child("lat").setValue(secLat);
                reference.child("lon").setValue(secLon);


                Toast.makeText(getApplicationContext(), "Task succeful!", Toast.LENGTH_SHORT).show();




            }
        });

        dlgMiDialogo.show();

    }
}
