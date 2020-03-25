package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterByRace extends AppCompatActivity implements View.OnClickListener {

    public ChipGroup races;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checker.check(this);
        setContentView(R.layout.activity_filter_by_race);
        races = findViewById(R.id.raceGroup);
        Button getButton = findViewById(R.id.getButton);
        getButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Chip raceSelected = (Chip) findViewById(races.getCheckedChipId());

        if (checkConnectivity()) {

            if (raceSelected != null) {

                String raceString = raceSelected.getText().toString();
                Intent i = new Intent(this, Results.class);
                i.putExtra("race", raceString);
                i.putExtra("filterType", "byRace");
                startActivity(i);

            } else {
                Toast.makeText(this, "SELECT A RACE FOR FILTERING", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText (FilterByRace.this, "Network not available",
                    Toast.LENGTH_LONG).show ();
        }



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
