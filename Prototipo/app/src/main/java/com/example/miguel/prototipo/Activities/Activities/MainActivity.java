package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Adapters.AdapterMissingDogs;
import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.Activities.Models.Usuario;
import com.example.miguel.prototipo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListView.OnItemClickListener {

    public static String dueño;
    private Intent inMaps;
    public static final String PATH_DOGS = "perros";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference reference = database.getReference(PATH_DOGS);
    private DatabaseReference reference2 = database.getReference("usuarios");

    private Intent inMissDog, inMyDogs, inAbout, inMatch;

    private Bundle bundle;

    private String nombre;
    private String myID;

    private List<Perro> perros = new ArrayList<>();
    private List<Usuario> users = new ArrayList<>();
    private AdapterMissingDogs adapterMissingDogs;

    private ListView listView;
    private ImageView imgNull, imgMainProfile;
    private TextView txtMainName, txtMainEmail;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Perro currentDog = dataSnapshot.getValue(Perro.class);
                    currentDog.setId(dataSnapshot.getKey());

                    if (currentDog.isEstatus()) {
                        perros.add(currentDog);
                    }
                    adapterMissingDogs.notifyDataSetChanged();
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

    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            handler.post(runnable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listMisDogs);
        imgNull = findViewById(R.id.imgNull);


        adapterMissingDogs = new AdapterMissingDogs(this, R.layout.list_item, perros);
        listView.setAdapter(adapterMissingDogs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        listView.setOnItemClickListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        thread.start();

        bundle = getIntent().getExtras();

        myID = bundle.getString("ID");

        dueño = myID;


        reference2.child(myID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                txtMainEmail = findViewById(R.id.txtMainEmail);
                txtMainName = findViewById(R.id.txtMainDueño);
                imgMainProfile = findViewById(R.id.imgMainProfile);

                nombre = dataSnapshot.child("nombre").getValue(String.class);
                String ape = dataSnapshot.child("apellido").getValue(String.class);
                String email = dataSnapshot.child("correo").getValue(String.class);
                int photo = dataSnapshot.child("foto").getValue(Integer.class);

                txtMainName.setText(nombre + " " + ape);
                txtMainEmail.setText(email);
                imgMainProfile.setImageResource(photo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_mydogs) {
            inMyDogs = new Intent(this, MyDogs.class);
            startActivity(inMyDogs);
        } else if (id == R.id.nav_match) {
            inMatch = new Intent(this, MatchMainActivity.class);
            startActivity(inMatch);
        } else if (id == R.id.nav_missings) {

        } else if (id == R.id.nav_collar) {
            inMaps = new Intent(this,ConfMapsActivity.class);
            startActivity(inMaps);
        } else if (id == R.id.nav_about) {
            inAbout = new Intent(this, About.class);
            startActivity(inAbout);
        } else if (id == R.id.nav_log_out) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        inMissDog = new Intent(this, EspecificMissingDog.class);
        bundle.putString("ID", perros.get(position).getId());
        bundle.putString("dueño", nombre);
        inMissDog.putExtras(bundle);
        startActivity(inMissDog);
    }

}