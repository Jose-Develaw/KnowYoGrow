package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {

    TextView descriptionText;
    StrainComplete sc;
    DBInterface dbInterface;
    boolean already = false;
    String fromResults = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fromResults = getIntent().getStringExtra("fromResults");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Detail.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                /*Intent intent = getIntent();
                if (intent.getStringExtra("fromResults") != null) {
                    ((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
                }
                finish();*/

            }
        });
        Intent intent = getIntent();
        sc = (StrainComplete) intent.getSerializableExtra("strainClicked");
        dbInterface = new DBInterface(Detail.this);
        dbInterface.open();
        Cursor c =  dbInterface.getStrainT();
        if (c.moveToFirst() && c != null) {
            do {
                int id = c.getInt(0);
                if (id == sc.getStrain().getId()) {already = true;}
            } while (c.moveToNext());
        }





        //Button toFav = findViewById(R.id.addFavButton);
        final FloatingActionButton floatingFav = findViewById(R.id.floatingFav);
        floatingFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbInterface = new DBInterface(Detail.this);
                dbInterface.open();
                Cursor c =  dbInterface.getStrainT();
                if (c.moveToFirst() && c != null) {
                    do {
                        int id = c.getInt(0);
                        if (id == sc.getStrain().getId()) {already = true;}
                    } while (c.moveToNext());
                }

                if (!already) {
                    already = true;
                    ArrayList<String> flavors = (ArrayList<String>) sc.getStrain().getFlavors();
                    ArrayList<String> positive_fx = (ArrayList<String>) sc.getStrain().getEffects().getPositive();
                    ArrayList<String> negative_fx = (ArrayList<String>) sc.getStrain().getEffects().getNegative();
                    ArrayList<String> medicos_fx = (ArrayList<String>) sc.getStrain().getEffects().getMedical();
                    floatingFav.setImageDrawable(getDrawable(android.R.drawable.star_big_on));
                    floatingFav.setCompatElevation(5);
                   // floatingFav.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));

                    dbInterface.newFavourite(sc.getStrain().getId(), sc.getName(), sc.getStrain().getRace(), flavors, positive_fx, negative_fx, medicos_fx);
                    Toast.makeText(Detail.this, "Added to favourites", Toast.LENGTH_LONG).show();

                } else {
                    already = false;
                    floatingFav.setImageDrawable(getDrawable(android.R.drawable.star_big_off));
                    floatingFav.setCompatElevation(50);
                    dbInterface.deleteFavourite(sc.getStrain().getId());
                    Toast.makeText(getLayoutInflater().getContext(), "Strain '"+sc.getName()+"' has been removed from favs",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        if(!already) {floatingFav.setCompatElevation(8);
         floatingFav.setImageDrawable(getDrawable(android.R.drawable.star_big_off));
            floatingFav.setCompatElevation(50);
       } else {

            floatingFav.setImageDrawable(getDrawable(android.R.drawable.star_big_on));
            floatingFav.setCompatElevation(5);

        }



        descriptionText= findViewById(R.id.descriptionText);
        Call<Description> descriptionCall = ApiService.getApiService().getDescription(sc.getStrain().getId());
        descriptionCall.enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                Description description = null;
                if(response.isSuccessful()) {
                    description = response.body();
                    descriptionText.setText(description.getDesc());

                }
                if (description == null || description.getDesc() == null) {
                    descriptionText= findViewById(R.id.descriptionText);
                    descriptionText.setText("Sorry, there is no description for this particular strain");
                }
            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {

            }
        });

        TextView name = findViewById(R.id.varietyNameDetail);
        TextView race = findViewById(R.id.varietyRaceDetail);
        TextView positiveText = findViewById(R.id.positiveText);
        TextView negativeText = findViewById(R.id.negativeText);
        TextView medicalText = findViewById(R.id.medicalText);
        TextView flavorText = findViewById(R.id.flavorText);


        name.setText(sc.getName().toUpperCase());
        String strainRace = sc.getStrain().getRace();
        race.setText(strainRace.toUpperCase());


        String poslist = sc.getStrain().getEffects().getPositive().toString();
        poslist = poslist.replace("[", "").replace("]", "");
        positiveText.setText(poslist);

        String neglist = sc.getStrain().getEffects().getNegative().toString();
        neglist = neglist.replace("[", "").replace("]", "");
        negativeText.setText(neglist);

        String medlist = sc.getStrain().getEffects().getMedical().toString();
        medlist = medlist.replace("[", "").replace("]", "");
        medicalText.setText(medlist);

        String flavors = sc.getStrain().getFlavors().toString();
        flavors = flavors.replace("[", "").replace("]", "");
        flavorText.setText(flavors);


        /*negativeText.setText(sc.getStrain().getEffects().getNegative().toString());
        medicalText.setText(sc.getStrain().getEffects().getMedical().toString());
        flavorText.setText(sc.getStrain().getFlavors().toString());*/

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
