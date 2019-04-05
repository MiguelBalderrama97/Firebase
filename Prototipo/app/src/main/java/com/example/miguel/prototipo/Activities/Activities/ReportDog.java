package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.miguel.prototipo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportDog extends AppCompatActivity{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(MainActivity.PATH_DOGS);

    private EditText etxtColonia;
    private Spinner spinDay, spinMonth, spinYear;
    private FloatingActionButton fabReport;

    private String fecha = "", dia, mes, annio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dog);

        etxtColonia = findViewById(R.id.etxtColonia);
        spinDay = findViewById(R.id.spinnerDia);
        spinMonth = findViewById(R.id.spinnerMes);
        spinYear = findViewById(R.id.spinnerAnnio);
        fabReport = findViewById(R.id.fabReport);

        setAdapter(R.array.days_array, spinDay);
        setAdapter(R.array.months_array, spinMonth);
        setAdapter(R.array.years_array, spinYear);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        spinDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dia = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mes = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                annio = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reference = database.getReference(MainActivity.PATH_DOGS).child(bundle.getString("ID"));

        fabReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etxtColonia.length() != 0){
                    fecha = dia + " de " + mes + " " + annio;
                    reference.child("estatus").setValue(true);
                    reference.child("colonia").setValue(etxtColonia.getText().toString());
                    reference.child("fecha").setValue(fecha);
                    Toast.makeText(ReportDog.this, "Perro reportado", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ReportDog.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setAdapter(int array, Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
