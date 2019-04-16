package com.example.miguel.prototipo.Activities.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.miguel.prototipo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditMyDog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;

    private EditText etxtNom, etxtEdad;
    private Spinner spinnerRaza;
    private RadioButton rbMas, rbFem;
    private ImageButton btnImage1, btnImage2, btnImage3, btnImage4;
    private FloatingActionButton fabEditDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_dog);

        etxtNom = findViewById(R.id.etxtEditNom);
        etxtEdad = findViewById(R.id.etxtEditEdad);
        spinnerRaza = findViewById(R.id.spinnerEditRaza);
        rbMas = findViewById(R.id.rbMasEditDog);
        rbFem = findViewById(R.id.rbFemEditDog);
        btnImage1 = findViewById(R.id.imgBtnEdit1);
        btnImage2 = findViewById(R.id.imgBtnEdit2);
        btnImage3 = findViewById(R.id.imgBtnEdit3);
        btnImage4 = findViewById(R.id.imgBtnEdit4);
        fabEditDog = findViewById(R.id.fabEditDog);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.razas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRaza.setAdapter(adapter);

        spinnerRaza.setOnItemSelectedListener(this);

        getSupportActionBar().setTitle("Edit dog ...");

        Bundle bundle = getIntent().getExtras();

        final String PATH_MY_DOG = bundle.getString("ID");
        reference = database.getReference(MainActivity.PATH_DOGS).child(PATH_MY_DOG);

        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fabEditDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
