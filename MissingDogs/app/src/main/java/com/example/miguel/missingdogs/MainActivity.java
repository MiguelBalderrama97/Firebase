package com.example.miguel.missingdogs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String PATH_FOOD = "perros";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference(PATH_FOOD);

    private List<Perros> perros = new ArrayList<>();
    private MyAdapter myAdapter;
    private ListView listView;
    private Button btnAdd;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listDogs);

        myAdapter = new MyAdapter(this, R.layout.list_item, perros);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
        btnAdd = findViewById(R.id.btnAdd);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perros comida = new Perros(R.mipmap.ic_perro2, "Perro"+(++counter),
                        "Labrador", "Miguel", "2 de octubre", "10 de marzo",false);
                reference.push().setValue(comida);
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Perros currentDog = dataSnapshot.getValue(Perros.class);
                if(currentDog.isEstatus()){
                    perros.add(currentDog);
                }
                myAdapter.notifyDataSetChanged();
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
        Toast.makeText(this, perros.get(position).isEstatus()+"", Toast.LENGTH_SHORT).show();
    }
}
