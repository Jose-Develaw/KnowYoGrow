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

public class FilterByEffect extends AppCompatActivity implements View.OnClickListener {

    public ChipGroup effects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filer_by_effect);

        effects = findViewById(R.id.effectsGroup);
        Button getButton = findViewById(R.id.getButton);
        getButton.setOnClickListener(this);

        Call<ArrayList<Effect>> effectListCall = ApiService.getApiService().getEffects();
        effectListCall.enqueue(new Callback<ArrayList<Effect>>() {
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
                        c.setCheckedIcon(ContextCompat.getDrawable(effects.getContext(), android.R.drawable.star_big_on));
                        effects.addView(c);

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Effect>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        Chip effectSelected = (Chip) findViewById(effects.getCheckedChipId());


        if (effectSelected != null) {

            String effectString = effectSelected.getText().toString();
            Intent i = new Intent(this, Results.class);
            i.putExtra("effect", effectString);
            i.putExtra("filterType", "byEffect");
            startActivity(i);
        } else {
            Toast.makeText(this, "SELECT AN EFFECT FOR FILTERING", Toast.LENGTH_LONG).show();
        }



    }
}
