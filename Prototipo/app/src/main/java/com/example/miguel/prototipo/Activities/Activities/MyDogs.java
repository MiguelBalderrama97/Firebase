package com.example.miguel.prototipo.Activities.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.miguel.prototipo.Activities.Adapters.AdapterMyDogs;
import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDogs extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    private ListView listMyDogs;

    private List<Perro> perros = new ArrayList<>();
    private AdapterMyDogs adapterMyDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dogs);
        getSupportActionBar().setTitle("My Dogs");

        listMyDogs = findViewById(R.id.listMyDogs);

        adapterMyDogs = new AdapterMyDogs(this, R.layout.list_item_mydogs, perros);
        listMyDogs.setAdapter(adapterMyDogs);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Perro currentDog = dataSnapshot.getValue(Perro.class);
                currentDog.setId(dataSnapshot.getKey());

                if(currentDog.getDueño().equals("Mike")){
                    perros.add(currentDog);
                }
                adapterMyDogs.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_dog) {
//            Perro perro = new Perro(R.mipmap.ic_perro2,5,"Perrito","Chihuahua","Mike",false);
//            perro.setColonia("Saucito");
//            perro.setFecha("11 de marzo");
//            perro.setImg1(R.mipmap.ic_perro);
//            perro.setImg2(R.mipmap.ic_perro3);
//            perro.setIm3(R.mipmap.ic_perro4);
//            reference.push().setValue(perro);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
