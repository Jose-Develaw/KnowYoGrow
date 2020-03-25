package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.valueOf;


public class Filter extends AppCompatActivity implements View.OnClickListener, Callback<ArrayList<Effect>>,  Serializable {


    public ChipGroup races, effects, flavors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Checker.check(this);

        Call<ArrayList<Effect>> effectListCall = ApiService.getApiService().getEffects();
        effectListCall.enqueue(this);
        Call<ArrayList<String>> flavorListCall = ApiService.getApiService().getFlavors();
        flavorListCall.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                ArrayList<String> flavorsList = (ArrayList<String>) response.body();

                for(int i = 0; i < flavorsList.size(); i++) {


                        Chip c = new Chip(flavors.getContext());
                        c.setRippleColor(ColorStateList.valueOf(ContextCompat.getColor(flavors.getContext(), R.color.colorAccent)));
                        c.setText(flavorsList.get(i));
                        c.setCheckable(true);
                        c.setChipStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(flavors.getContext(), R.color.colorPrimary)));
                        c.setChipStrokeWidth(5);
                        c.setCheckedIcon(ContextCompat.getDrawable(flavors.getContext(), android.R.drawable.star_big_on));
                        flavors.addView(c);


                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
        races = findViewById(R.id.raceGroup);
        effects = findViewById(R.id.effectsGroup);
        flavors = findViewById(R.id.flavoursGroup);


        Button getButton = findViewById(R.id.getButton);
        getButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        Chip raceSelected = (Chip) findViewById(races.getCheckedChipId());
        Chip effectSelected = (Chip) findViewById(effects.getCheckedChipId());
        Chip flavorSelected = (Chip) findViewById(flavors.getCheckedChipId());

        if (checkConnectivity()) {

            if (raceSelected != null && effectSelected != null && flavorSelected != null) {

                String raceString = raceSelected.getText().toString();
                String effectString = effectSelected.getText().toString();
                String flavorString = flavorSelected.getText().toString();

                Intent i = new Intent(this, Results.class);

                i.putExtra("race", raceString);
                i.putExtra("effect", effectString);
                i.putExtra("flavor", flavorString);
                i.putExtra("filterType", "all");
                startActivity(i);

            } else {
                Toast.makeText(this, "SELECT AT LEAST ONE FILTER FOR EACH CATEGORY", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText (Filter.this, "Network not available",
                    Toast.LENGTH_LONG).show ();
        }

    }

    @Override
    public void onResponse(Call<ArrayList<Effect>> call, Response<ArrayList<Effect>> response) {

        ArrayList<Effect> effectList = (ArrayList<Effect>) response.body();

        for(int i = 0; i < effectList.size(); i++) {

            if (effectList.get(i).getType().equals("positive") || effectList.get(i).getType().equals("medical")) {

                Chip c = new Chip(effects.getContext());
                c.setRippleColor(ColorStateList.valueOf(ContextCompat.getColor(effects.getContext(), R.color.colorAccent)));
                c.setText(effectList.get(i).getEffect());
                c.setCheckable(true);
                c.setChipStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(effects.getContext(), R.color.colorPrimary)));
                c.setChipStrokeWidth(5);
                c.setCheckedIcon(ContextCompat.getDrawable(this, android.R.drawable.star_big_on));
                effects.addView(c);

            }
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Effect>> call, Throwable t) {

    }

    public boolean checkConnectivity() {

        // Obtenemos un gestor de las conexiones de red
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService (this.CONNECTIVITY_SERVICE);

        // Obtenemos el estado de la red
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Si está conectado
        if (networkInfo != null && networkInfo.isConnected ()) {

            return true;

        } else {

            return false;
        }
    }
}
