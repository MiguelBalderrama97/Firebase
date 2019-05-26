package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Adapters.AdapterMyDogsMatch;
import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchPartnerActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private Bundle bundle, bundle2;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    private List<Perro> perros = new ArrayList<>();
    private AdapterMyDogsMatch adapterMyDogs = new AdapterMyDogsMatch(this, R.layout.list_item_mydogs_match, perros);

    private ListView listSearchPartner;

    private Intent inEspecificMatch;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

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
        setContentView(R.layout.activity_search_partner);

        listSearchPartner = findViewById(R.id.listSearchPartner);

        getSupportActionBar().setTitle("Elige una pareja");
        listSearchPartner.setAdapter(adapterMyDogs);
//        thread.start();
        listSearchPartner.setOnItemClickListener(this);

        bundle = getIntent().getExtras();

//        String ID = bundle.getString("ID");


        reference.addChildEventListener(new ChildEventListener() {

            boolean sexo = bundle.getBoolean("sexo");
            String raza = bundle.getString("raza");

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Perro currentDog = dataSnapshot.getValue(Perro.class);
                currentDog.setId(dataSnapshot.getKey());

                if (!currentDog.getDueño().equals(MainActivity.dueño) && currentDog.isSexo() != sexo && currentDog.getRaza().equals(raza)) {
                    perros.add(currentDog);
                }
                adapterMyDogs.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Perro currentDog = perros.get(position);

        bundle2 = new Bundle();
        inEspecificMatch = new Intent(this, EspecificMatchDog.class);

        String ID = currentDog.getId();
        String nombre = currentDog.getNombre();
        String raza = currentDog.getRaza();
        int edad = currentDog.getEdad();
        String dueño = currentDog.getDueño();
        int img1 = currentDog.getIcon();
        int img2 = currentDog.getImg1();
        int img3 = currentDog.getImg2();
        int img4 = currentDog.getIm3();

        bundle2.putString("ID", ID);
        bundle2.putString("nombre", nombre);
        bundle2.putString("raza", raza);
        bundle2.putString("dueño", dueño);
        bundle2.putInt("edad", edad);
        bundle2.putInt("img1", img1);
        bundle2.putInt("img2", img2);
        bundle2.putInt("img3", img3);
        bundle2.putInt("img4", img4);

        inEspecificMatch.putExtras(bundle2);

        startActivity(inEspecificMatch);
    }
}
