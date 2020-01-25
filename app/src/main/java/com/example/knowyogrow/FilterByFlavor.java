package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterByFlavor extends AppCompatActivity implements View.OnClickListener {

    public ChipGroup flavors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_flavor);

        flavors = findViewById(R.id.flavoursGroup);
        Button getButton = findViewById(R.id.getButton);
        getButton.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View view) {

        Chip flavorSelected = (Chip) findViewById(flavors.getCheckedChipId());


        if (flavorSelected != null) {

            String flavorString = flavorSelected.getText().toString();
            Intent i = new Intent(this, Results.class);
            i.putExtra("flavor", flavorString);
            i.putExtra("filterType", "byFlavor");
            startActivity(i);
        } else {
            Toast.makeText(this, "SELECT A FLAVOR FOR FILTERING", Toast.LENGTH_LONG).show();
        }



    }
}
