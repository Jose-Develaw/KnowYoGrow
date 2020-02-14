package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        setContentView(R.layout.activity_filter_by_race);
        races = findViewById(R.id.raceGroup);
        Button getButton = findViewById(R.id.getButton);
        getButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Chip raceSelected = (Chip) findViewById(races.getCheckedChipId());


        if (raceSelected != null) {

            String raceString = raceSelected.getText().toString();
            Intent i = new Intent(this, Results.class);
            i.putExtra("race", raceString);
            i.putExtra("filterType", "byRace");
            startActivity(i);

        } else {
            Toast.makeText(this, "SELECT A RACE FOR FILTERING", Toast.LENGTH_LONG).show();
        }



    }
}
