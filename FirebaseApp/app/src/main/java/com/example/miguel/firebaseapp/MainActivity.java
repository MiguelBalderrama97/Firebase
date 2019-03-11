package com.example.miguel.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE = "message";

    private EditText etxtNom;
    private Button btnSend, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtMsg = findViewById(R.id.txtMsg);

        etxtNom = findViewById(R.id.etxtNom);
        btnSend = findViewById(R.id.btnSend);
        btnList = findViewById(R.id.btnList);

        final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtMsg.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error al consultar en Firebase", Toast.LENGTH_LONG).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);

                reference.setValue(etxtNom.getText().toString().trim());
                etxtNom.setText("");
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, list.class);
                startActivity(intent);
            }
        });
    }
}
