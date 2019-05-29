package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.miguel.prototipo.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("usuarios");
    private DatabaseReference reference2;

    private Intent inSign, inLogin;

    private Button btnSign, btnLogin;
    private EditText etxtEmail, etxtPass;

    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        btnSign = findViewById(R.id.btnSignIn);
        etxtPass = findViewById(R.id.txtVwPwd);
        etxtEmail = findViewById(R.id.txtVwEmail);
        btnLogin = findViewById(R.id.btnLogin);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inSign = new Intent(LogActivity.this, SignActivity.class);
                startActivity(inSign);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etxtEmail.getText().toString();
                String pass = etxtPass.getText().toString();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LogActivity.this, "Complete campos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, pass);
                }
            }
        });

    }

    private void loginUser(final String email, final String pass) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(LogActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Query query = reference.orderByChild("correo").equalTo(email);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                        key = datasnapshot.getKey();
                                    }

//                                    Toast.makeText(LogActivity.this, key+" anterior", Toast.LENGTH_SHORT).show();

                                    Bundle bundle = new Bundle();
                                    bundle.putString("ID", key);
                                    inLogin = new Intent(LogActivity.this, MainActivity.class);
                                    inLogin.putExtras(bundle);
                                    startActivity(inLogin);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(LogActivity.this, "Ups! Algo falló", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        etxtPass.setText("");
//        etxtEmail.setText("");
//
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Usuario user = dataSnapshot.getValue(Usuario.class);
//                user.setId(dataSnapshot.getKey());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
