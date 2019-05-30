package com.example.miguel.prototipo.Activities.Activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.miguel.prototipo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("usuarios").child(MainActivity.dueño);

    private GoogleMap mMap;
    private float DEFAULT_ZOOM= 18;
    Circle myCircle;
    private double dogLat =28.70382, dogLon =-106.124489;
    private int radio = 3;
    private double cirLat =28.70382, cirLon =-106.124489;
    String s="";
    MarkerOptions markOpt;
    Marker dogMarker;

    Handler hMyHandler = new Handler();

    Runnable rRunInterface = new Runnable() {
        @Override
        public void run() {
            if(!s.equals("")){
                try {
                    JSONObject jsData = new JSONObject(s);
                    System.out.println(jsData);
                    dogLat = jsData.getDouble("lat");
                    dogLon = jsData.getDouble("lon");
                    dogMarker.setPosition(new LatLng(dogLat, dogLon));
                    isDogOnSafeArea();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    Thread tHiloMain = new Thread(){
        @Override
        public void run() {
            super.run();
            while (true){
                try{
                    Thread.sleep(5000);
                    String url = "https://pelavacas.000webhostapp.com/data.php";
                    String sResu = "";
                    //Conexion
                    try {
                        URL myUrl = new URL(url);
                        HttpURLConnection httpCon = (HttpURLConnection) myUrl.openConnection();
                        if(httpCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                            BufferedReader dataJSON = new BufferedReader(
                                    new InputStreamReader(
                                            httpCon.getInputStream()
                                    )
                            );
                            sResu = dataJSON.readLine();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    s = sResu;
                    hMyHandler.post(rRunInterface);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tHiloMain.start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng dogPosition = new LatLng(dogLat, dogLon);
        markOpt = new MarkerOptions().position(dogPosition).title("Your dog is here!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.petmarker));
        dogMarker = mMap.addMarker(markOpt);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dogPosition,DEFAULT_ZOOM));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                radio = dataSnapshot.child("radio").getValue(Integer.class);
                cirLat  = dataSnapshot.child("lat").getValue(Double.class);
                cirLon = dataSnapshot.child("lon").getValue(Double.class);

                myCircle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(cirLat,cirLon))
                        .radius(radio)
                        .strokeColor(Color.GREEN)
                        .fillColor(Color.GREEN)
                );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void isDogOnSafeArea(){
        float[] dist = new float[2];
        //TODO: Coordenadas de la zona seegura sacadas de firebase
        Location.distanceBetween(dogLat, dogLon,cirLat,cirLon,dist);
        if(dist[0]>myCircle.getRadius()){
            Toast.makeText(getApplicationContext(),"Mascota fuera de zona segura",Toast.LENGTH_SHORT).show();
        }
    }
}
