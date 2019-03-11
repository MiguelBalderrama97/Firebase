package com.example.miguel.firebaseapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Comida> comidas;

    public MyAdapter(Context context, int layout, List<Comida> comidas) {
        this.context = context;
        this.layout = layout;
        this.comidas = comidas;
    }

    @Override
    public int getCount() {
        return comidas.size();
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

        TextView txtNom, txtPrecio;

        if(convertView == null){ //NO EXISTE LA FILA, HAY QUE CREARLA
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        txtNom = convertView.findViewById(R.id.txtNom);
        txtPrecio = convertView.findViewById(R.id.txtPrecio);

        Comida current = comidas.get(position);
        txtNom.setText(current.getNombre());
        txtPrecio.setText("$"+current.getPrecio());

        return convertView;
    }
}
