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

public class MatchMainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    private List<Perro> perros = new ArrayList<>();
    private AdapterMyDogsMatch adapterMyDogs = new AdapterMyDogsMatch(this, R.layout.list_item_mydogs_match, perros);

    private Intent inSearchPartner;

    private ListView listMyMatch;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Perro currentDog = dataSnapshot.getValue(Perro.class);
                    currentDog.setId(dataSnapshot.getKey());

                    if (currentDog.getDueño().equals(MainActivity.dueño)) {
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
    };

    private Thread thread = new Thread(){
        @Override
        public void run() {
            super.run();
            handler.post(runnable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_main);

        getSupportActionBar().setTitle("Elige un perro");

        listMyMatch = findViewById(R.id.listMyMatch);

        listMyMatch.setAdapter(adapterMyDogs);

        thread.start();

        listMyMatch.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Perro currentDog = perros.get(position);
//        Toast.makeText(this, currentDog.getId()+"", Toast.LENGTH_SHORT).show();
        String ID = currentDog.getId();
        String raza = currentDog.getRaza();
        Boolean sexo = currentDog.isSexo();
        Bundle bundle = new Bundle();
        inSearchPartner = new Intent(this, SearchPartnerActivity.class);
        bundle.putString("ID", ID);
        bundle.putString("raza", raza);
        bundle.putBoolean("sexo", sexo);
        inSearchPartner.putExtras(bundle);
        startActivity(inSearchPartner);
    }
}
