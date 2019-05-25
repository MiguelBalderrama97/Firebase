package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miguel.prototipo.R;

import java.util.ArrayList;
import java.util.List;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;

public class EspecificMatchDog extends AppCompatActivity {

    private Bundle bundle;

    private TextView txtRaza, txtEdad, txtDueño;
    private FloatingActionButton fabMatch;
    private Intent inMarcar;

    private EasySlider easySlider;
    private List<SliderItem> sliderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especific_match_dog);

        txtRaza = findViewById(R.id.txtMatchRaza);
        txtEdad = findViewById(R.id.txtMatchEdad);
        txtDueño = findViewById(R.id.txtDueñoMatch);
        easySlider = findViewById(R.id.sliderMatch);
        fabMatch = findViewById(R.id.fabMatch);

        bundle = getIntent().getExtras();

        String nombre = bundle.getString("nombre");
        String raza = bundle.getString("raza");
        String dueño = bundle.getString("dueño");
        int edad = bundle.getInt("edad");
        int img1 = bundle.getInt("img1");
        int img2 = bundle.getInt("img2");
        int img3 = bundle.getInt("img3");
        int img4 = bundle.getInt("img4");

        getSupportActionBar().setTitle(nombre);

        sliderItems.add(new SliderItem(nombre, img1));
        sliderItems.add(new SliderItem(nombre, img2));
        sliderItems.add(new SliderItem(nombre, img3));
        sliderItems.add(new SliderItem(nombre, img4));

        easySlider.setPages(sliderItems);

        txtRaza.setText(raza);
        txtDueño.setText(dueño);
        txtEdad.setText(edad+" años");

        fabMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTel = "tel:6141227624";
                inMarcar = new Intent(Intent.ACTION_DIAL, Uri.parse(sTel));
                startActivity(inMarcar);
            }
        });
    }
}
