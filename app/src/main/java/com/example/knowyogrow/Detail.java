package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        StrainComplete sc = (StrainComplete) i.getSerializableExtra("strainClicked");

        TextView name = findViewById(R.id.varietyNameDetail);
        TextView race = findViewById(R.id.varietyRaceDetail);
        TextView positiveText = findViewById(R.id.positiveText);
        TextView negativeText = findViewById(R.id.negativeText);
        TextView medicalText = findViewById(R.id.medicalText);
        TextView flavorText = findViewById(R.id.flavorText);

        name.setText(sc.getName());
        String strainRace = sc.getStrain().getRace();
        race.setText(strainRace.toUpperCase());
        positiveText.setText(sc.getStrain().getEffects().getPositive().toString());
        negativeText.setText(sc.getStrain().getEffects().getNegative().toString());
        medicalText.setText(sc.getStrain().getEffects().getMedical().toString());
        flavorText.setText(sc.getStrain().getFlavors().toString());

        ImageView imageDetail = (ImageView) findViewById(R.id.imageDetail);
        switch (strainRace) {

            case "sativa" : Picasso.get().load("https://p1.pxfuel.com/preview/599/896/1019/marijuana-cannabis-hash-leaf.jpg").resize(250, 250).transform(new ImageRoundCorners()).into(imageDetail);
                break;
            case "indica" : Picasso.get().load("https://cdn.pixabay.com/photo/2017/03/17/20/22/cannabis-2152604_960_720.jpg").resize(250, 250).transform(new ImageRoundCorners()).into(imageDetail);
                break;
            case "hybrid" : Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/5/5a/White_widow.jpg").resize(250, 250).transform(new ImageRoundCorners()).into(imageDetail);
                break;
        }


    }
}
