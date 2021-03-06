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

public class AdapterMyDogsMatch extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Perro> perros;

    public AdapterMyDogsMatch(Context context, int layout, List<Perro> perros) {
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

        TextView txtNom;
        ImageView imgDog;

        if(convertView == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        txtNom = convertView.findViewById(R.id.txtMyDogMatch);
        imgDog = convertView.findViewById(R.id.imgMyDogMatch);

        Perro currentDog = perros.get(position);
        txtNom.setText(currentDog.getNombre());
        imgDog.setImageResource(currentDog.getIcon());

        return convertView;
    }
}
