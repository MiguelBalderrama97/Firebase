package com.example.dogit_prototype.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dogit_prototype.Adapters.MyAdapter;
import com.example.dogit_prototype.Models.Perro;
import com.example.dogit_prototype.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListView.OnItemClickListener  {


    public static String PATH_DOGS = "perros";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference(PATH_DOGS);

    private Intent inMissDog;

    private List<Perro> perros = new ArrayList<>();
    private MyAdapter myAdapter ;

    private ListView listView;
    private ImageView imgNull;

    private boolean vacio = true;
    //Comentario chido obvis
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listMisDogs);
        imgNull = findViewById(R.id.imgNull);

        myAdapter = new MyAdapter(this, R.layout.list_item, perros);
        listView.setAdapter(myAdapter);

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

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Perro currentDog = dataSnapshot.getValue(Perro.class);
                currentDog.setId(dataSnapshot.getKey());
//                if(currentDog.isEstatus()){
                perros.add(currentDog);
                vacio = false;
//                }
                myAdapter.notifyDataSetChanged();
                if(vacio){
                    listView.setVisibility(View.GONE);
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Perro perro = new Perro(R.mipmap.ic_perro2,5,"Perrito","Chihuahua","Mike",false);
            perro.setColonia("Saucito");
            perro.setFecha("11 de marzo");
            perro.setImg1(R.mipmap.ic_perro);
            perro.setImg2(R.mipmap.ic_perro3);
            perro.setIm3(R.mipmap.ic_perro4);
            reference.push().setValue(perro);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_mydogs) {
//
//        } else if (id == R.id.nav_match) {
//
//        } else if (id == R.id.nav_missings) {
//
//        } else if (id == R.id.nav_collar) {
//
//        } else if (id == R.id.nav_about) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        inMissDog = new Intent(this, EspecificMissingDog.class);
        bundle.putString("ID",perros.get(position).getId());
        inMissDog.putExtras(bundle);
        startActivity(inMissDog);
    }
}
