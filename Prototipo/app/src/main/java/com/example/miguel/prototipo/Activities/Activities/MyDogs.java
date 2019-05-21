package com.example.miguel.prototipo.Activities.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Adapters.AdapterMyDogs;
import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyDogs extends AppCompatActivity implements ListView.OnItemClickListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    private ListView listMyDogs;

    private List<Perro> perros = new ArrayList<>();
    private AdapterMyDogs adapterMyDogs = new AdapterMyDogs(this, R.layout.list_item_mydogs, perros);

    private Intent inAddDog, inReport, inEdit;
    private Bundle bDatos, bEdit;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Perro currentDog = dataSnapshot.getValue(Perro.class);
                    currentDog.setId(dataSnapshot.getKey());

                    if (currentDog.getDueño().equals("Mike")) {
                        perros.add(currentDog);
                    }
                    adapterMyDogs.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        setContentView(R.layout.activity_my_dogs);
        getSupportActionBar().setTitle("My Dogs");

        listMyDogs = findViewById(R.id.listMyDogs);

        listMyDogs.setAdapter(adapterMyDogs);

        listMyDogs.setOnItemClickListener(this);

        thread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterMyDogs.notifyDataSetChanged();
//        Toast.makeText(this, "START!", Toast.LENGTH_SHORT).show();
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
            inAddDog = new Intent(this, AddMyDog.class);
            startActivity(inAddDog);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final Perro currentDog = perros.get(position);

        final DatabaseReference reference2 = reference.child(currentDog.getId());

        final Dialog dlgMiDialog;
        dlgMiDialog = new Dialog(MyDogs.this);
        //EL LAYOUT
        dlgMiDialog.setContentView(R.layout.cuadro_dialogo_mydogs);

        TextView txtNom;
        final Button btnEdit, btnStatus;

        txtNom = dlgMiDialog.findViewById(R.id.txtNomCuadroDialMyDog);
        btnEdit = dlgMiDialog.findViewById(R.id.btnEditCuadroDialMyDog);
        btnStatus = dlgMiDialog.findViewById(R.id.btnStatusCuadroDialMyDog);

        txtNom.setText(currentDog.getNombre().toUpperCase());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgMiDialog.dismiss();
                Perro currentDog = perros.get(position);
                bEdit = new Bundle();
                inEdit = new Intent(MyDogs.this, EditMyDog.class);
                bEdit.putString("ID", currentDog.getId());
                bEdit.putString("Nombre", currentDog.getNombre());
                bEdit.putInt("Edad", currentDog.getEdad());
                bEdit.putInt("Spinner", currentDog.getSpinner());
                bEdit.putBoolean("Genero", currentDog.isSexo());
                bEdit.putInt("Img1", currentDog.getIcon());
                bEdit.putInt("Img2", currentDog.getImg1());
                bEdit.putInt("Img3", currentDog.getImg2());
                bEdit.putInt("Img4", currentDog.getIm3());
                inEdit.putExtras(bEdit);
                startActivity(inEdit);
            }
        });

        if (currentDog.isEstatus()) {
            btnStatus.setText("ENCONTRADO");
            btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference2.child("estatus").setValue(false);
                    reference2.child("fecha").setValue("");
                    reference2.child("colonia").setValue("");
                    startActivity(getIntent());
                    Toast.makeText(MyDogs.this, currentDog.getNombre() + " fue encontrado", Toast.LENGTH_SHORT).show();
                    dlgMiDialog.dismiss();
                }
            });
        } else {
            btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlgMiDialog.dismiss();
                    inReport = new Intent(MyDogs.this, ReportDog.class);
                    bDatos = new Bundle();
                    bDatos.putString("ID", currentDog.getId());
                    inReport.putExtras(bDatos);
                    startActivity(inReport);
                }
            });
        }

        dlgMiDialog.show();
    }

}
