package com.example.miguel.prototipo.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miguel.prototipo.Activities.Models.Perro;
import com.example.miguel.prototipo.R;

import java.util.List;

public class AdapterMissingDogs extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Perro> perros;

    public AdapterMissingDogs(Context context, int layout, List<Perro> perros) {
        this.context = context;
        this.layout = layout;
        this.perros = perros;
    }

    @Override
    public int getCount() {
        return perros.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtNom, txtCol, txtFecha, txtRaza;
        ImageView imgDog;

        if(convertView == null){ //NO EXISTE LA FILA, HAY QUE CREARLA
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        imgDog = convertView.findViewById(R.id.imgMisDog);
        txtNom = convertView.findViewById(R.id.txtMisNom);
        txtCol = convertView.findViewById(R.id.txtMisColonia);
        txtFecha = convertView.findViewById(R.id.txtMisFecha);
        txtRaza = convertView.findViewById(R.id.txtMisRaza);

        Perro currentDog = perros.get(position);
        //TODO: Arreglar icono
        //imgDog.setImageResource(currentDog.getIcon());
        txtNom.setText(currentDog.getNombre());
        txtCol.setText("Colonia: "+currentDog.getColonia());
        txtFecha.setText("Fecha: "+currentDog.getFecha());
        txtRaza.setText("Raza: "+currentDog.getRaza());

        return convertView;
    }
}
