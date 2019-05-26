package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.prototipo.Activities.Models.Usuario;
import com.example.miguel.prototipo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class SignActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private StorageReference mStorage;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("usuarios");

    private TextView txtImagenSign;
    private ImageView imgProfileSign, imgStatusImage;
    private Button btnSignReg;
    private EditText etxtEmailSign, etxtPassSign, etxtPassSign2, etxtNomSign, etxtApeSign, etxtPhoneSign;

    private Uri imageUri;

    private boolean confirm = false;

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        txtImagenSign = findViewById(R.id.txtImagenSign);
        imgProfileSign = findViewById(R.id.imgProfileSign);
        imgStatusImage = findViewById(R.id.imgStatusImage);
        btnSignReg = findViewById(R.id.btnSignReg);
        etxtPassSign = findViewById(R.id.etxtPassSign);
        etxtPassSign2 = findViewById(R.id.etxtPassSign2);
        etxtEmailSign = findViewById(R.id.etxtEmailSign);
        etxtNomSign = findViewById(R.id.etxtNomSign);
        etxtApeSign = findViewById(R.id.etxtApeSign);
        etxtPhoneSign = findViewById(R.id.etxtPhoneSign);

        mStorage = FirebaseStorage.getInstance().getReference();

        txtImagenSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSignReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etxtEmailSign.getText().toString();
                String pass = etxtPassSign.getText().toString();
                String pass2 = etxtPassSign2.getText().toString();
                String nom = etxtNomSign.getText().toString();
                String ape = etxtApeSign.getText().toString();
                String phone = etxtPhoneSign.getText().toString();

                if (email.isEmpty() || pass.isEmpty() || pass2.isEmpty() || !confirm || nom.isEmpty() || ape.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(SignActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pass.equals(pass2)) {
                        Toast.makeText(SignActivity.this, "Contraseña no coincide", Toast.LENGTH_SHORT).show();
                    } else {
                        createUser(email, pass);
                    }
                }
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgProfileSign.setImageURI(imageUri);
            imgStatusImage.setImageResource(R.mipmap.ic_checked);
            confirm = true;

            StorageReference filePath = mStorage.child("UsersPhotos").child(imageUri.getLastPathSegment());

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
        }
    }

    private void createUser(final String email, final String pass) {

        final String email_2 = etxtEmailSign.getText().toString();
        final String pass_2 = etxtPassSign.getText().toString();
        final String nom = etxtNomSign.getText().toString();
        final String ape = etxtApeSign.getText().toString();
        final String phone = etxtPhoneSign.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(SignActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignActivity.this, "Cuenta registrada", Toast.LENGTH_SHORT).show();
                            createUserDB(nom, ape, email_2, pass_2, phone, R.mipmap.ic_huella_roj);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignActivity.this, "Este correo ya esta registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignActivity.this, "Ups! Algo falló", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void createUserDB(String nom, String ape, String email, String pass, String phone, int photo) {
        Usuario user = new Usuario(nom, ape, email, pass, phone, photo);
        reference.push().setValue(user);
    }
}
