package com.example.miguel.prototipo.Activities.Activities;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Models.Perro;
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
import com.google.firebase.database.ChildEventListener;
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
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText searchBar;
    Button btnSearch;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("usuarios").child(MainActivity.dueño);
    private DatabaseReference referenceDog = database.getReference(MainActivity.PATH_DOGS);

    private List<Perro> perros = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();
    private Marker markAux;


    private GoogleMap mMap;
    float DEFAULT_ZOOM= 18;
    Circle myCircle;
    private double dogLat =28.70382, dogLon =-106.124489;
    LatLng dogPosition;
    LatLng currentDogPosition;

    private int radio = 3;
    private double cirLat =28.70382, cirLon =-106.124489;

    String s="";
    MarkerOptions markOpt;
    Marker dogMarker;
    //Variables para mover la ubicación del perro
    double rand,dLat,dLon;


    Handler hMyHandler = new Handler();

    Runnable rRunInterface = new Runnable() {
        @Override
        public void run() {
            if(!s.equals("")){
                try {
                    mMap.clear();
                    myCircle = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(cirLat,cirLon))
                            .radius(radio)
                            .strokeColor(Color.GREEN)
                            .fillColor(Color.GREEN)
                    );
                    JSONObject jsData = new JSONObject(s);
                    System.out.println(jsData);
                    dogLat = jsData.getDouble("lat");
                    dogLon = jsData.getDouble("lon");

                    for(Perro currentPerro: perros){

                        if(Math.random()*10>5){
                            rand = Math.random() / 9000;
                            dLat = dogLat - 6*rand;
                            dLon = dogLon - 3*rand;
                        }else{
                            rand = Math.random() / 9000;
                            dLat = dogLat + 3*rand;
                            dLon = dogLon + 6*rand;
                        }

                        Log.wtf("myTag", "lat"+dLat+" lon"+dLon+" rand" +rand);

                        referenceDog.child(currentPerro.getId()).child("lat").setValue(dLat);
                        referenceDog.child(currentPerro.getId()).child("lon").setValue(dLon);
                        referenceDog.child(currentPerro.getId()).child("onSafeZone").setValue(isDogOnSafeArea(currentPerro.getLat(),currentPerro.getLon()));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            referenceDog.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Perro currentDog = dataSnapshot.getValue(Perro.class);
                    currentDog.setId(dataSnapshot.getKey());

                    if (currentDog.getDueño().equals(MainActivity.dueño)) {
                        perros.add(currentDog);

                        dogPosition = new LatLng(currentDog.getLat(),currentDog.getLon());

                        markAux= mMap.addMarker(new MarkerOptions().position(dogPosition)
                                .title(currentDog.getNombre()+" is here!")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.petmarker)));
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
        setContentView(R.layout.activity_test);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchBar = findViewById(R.id.searchBar);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchDog = searchBar.getText().toString();
                for (Perro dog : perros){
                    if(dog.getNombre().equals(searchDog)){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                dog.getLat(),dog.getLon()),DEFAULT_ZOOM));
                        break;
                    }
                }
            }
        });

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

//        markOpt = new MarkerOptions().position(dogPosition).title("Your dog is here!")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.petmarker));
//        dogMarker = mMap.addMarker(markOpt);



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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cirLat,cirLon),DEFAULT_ZOOM));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean isDogOnSafeArea(double dogLat,double dogLon){
        boolean ban = true;
        float[] dist = new float[2];
        //TODO: Coordenadas de la zona seegura sacadas de firebase
        Location.distanceBetween(dogLat, dogLon,cirLat,cirLon,dist);
        if(dist[0]>myCircle.getRadius()){
            ban =false;
        }

        return ban;
    }

}
