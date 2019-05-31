package com.example.miguel.prototipo.Activities.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miguel.prototipo.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConfMapsActivity extends AppCompatActivity {

    Intent inMapon, inSecurity;
    private int PLACE_PICKER_REQUEST = 669;
    double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_maps);

//        inMapon = new Intent(this,MapsActivity.class);
        inMapon = new Intent(this,TestActivity.class);
        inSecurity = new Intent(this, SecurityZoneActivity.class);

        ConectionGps myConection = new ConectionGps();
        myConection.execute();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnLookDog:
                startActivity(inMapon);
                break;
            case R.id.btnPick:
                startActivity(inSecurity);
                break;
        }
    }

    class ConectionGps extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String sVal ="";
//            Toast.makeText(getApplicationContext(),"S"+s,Toast.LENGTH_SHORT).show();
            if(!s.equals("")){
                try {
                    JSONObject jsData = new JSONObject(s);
                    lat = jsData.getDouble("lat");
                    lon = jsData.getDouble("lon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            String url = "https://pelavacas.000webhostapp.com/data.php";
            String sResu = "";

            try {
                URL myUrl = new URL(url);
                HttpURLConnection httpCon = (HttpURLConnection) myUrl.openConnection();
                if(httpCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader dataJSON = new BufferedReader(
                            new InputStreamReader(
                                    httpCon.getInputStream()
                            )
                    );
                    sResu = dataJSON.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResu;
        }
    }
}
