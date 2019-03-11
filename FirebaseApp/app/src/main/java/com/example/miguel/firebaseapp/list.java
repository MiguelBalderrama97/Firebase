package com.example.miguel.firebaseapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {

    private static final String PATH_FOOD = "food";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference(PATH_FOOD);

    private EditText etxtNombre, etxtPrecio;
    private Button btnAdd;
    private ListView listComidas;

    private List<Comida> comidas = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        etxtNombre = findViewById(R.id.etxtNom);
        etxtPrecio = findViewById(R.id.etxtPrecio);
        btnAdd = findViewById(R.id.btnAdd);
        listComidas = findViewById(R.id.listComida);

        myAdapter = new MyAdapter(list.this,R.layout.item_comida,comidas);
        listComidas.setAdapter(myAdapter);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comida comida = dataSnapshot.getValue(Comida.class);
                comidas.add(comida);
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = etxtNombre.getText().toString();
                String pre = etxtPrecio.getText().toString();

                Comida comida = new Comida(nom, pre);

                reference.push().setValue(comida);
                etxtNombre.setText("");
                etxtPrecio.setText("");
            }
        });

    }
}
