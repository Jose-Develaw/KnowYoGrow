package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Results extends AppCompatActivity implements ResultAdapter.Listener, Callback<Map<String, Strain>> {

    ArrayList<StrainComplete> datos;
    RecyclerView myRecycler;
    String race;
    String effect;
    String flavor;
    String filterType;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Results.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        TextView filteredBy = findViewById(R.id.filteredBy);


        Intent i = getIntent();

        if (i.getStringExtra("race") != null) {
            race = i.getStringExtra("race");
            String display = "Race: " + race.toString();
            filteredBy.setText(display);
        } else {
            race = "";
        }

        if (i.getStringExtra("effect") != null) {
            effect = i.getStringExtra("effect");
            String display = "Effect: " + effect.toString();
            filteredBy.setText(display);
        } else {
            effect = "";
        }

        if (i.getStringExtra("flavor") != null) {
            flavor = i.getStringExtra("flavor");
            String display = "Flavor: " + flavor.toString();
            filteredBy.setText(display);
        } else {
            flavor = "";
        }

        filterType = i.getStringExtra("filterType");
        datos = new ArrayList<>();

        myRecycler = findViewById(R.id.myRecycler);
        myRecycler.setLayoutManager( new LinearLayoutManager(this));
        Call<Map<String, Strain>> resultados = ApiService.getApiService().getAll();
        resultados.enqueue(this);

    }

    @Override
    public void onResultClick(int position) {
        StrainComplete sc = datos.get(position);
        Intent i = new Intent(Results.this, Detail.class);
        i.putExtra("finisher", new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                Results.this.finish();
            }
        });
        i.putExtra("strainClicked", sc);
        i.putExtra("fromResults", "fromResults");
        startActivityForResult(i,1);

    }

    @Override
    public void onResponse(Call<Map<String, Strain>> call, Response<Map<String, Strain>> response) {

        Map<String, Strain> result = response.body();
        boolean containsEffect = false;

        if (filterType.equals("all")) {

            for(Map.Entry<String, Strain> entry : result.entrySet()) {

                String name = entry.getKey();
                Strain strain = entry.getValue();

                if (strain.getEffects().getMedical().contains(effect) ||
                        strain.getEffects().getNegative().contains(effect) ||
                        strain.getEffects().getPositive().contains(effect)) {
                    containsEffect = true;

                }
                if (strain.getFlavors().contains(flavor) && strain.getRace().equals(race.toLowerCase()) && containsEffect) {
                    StrainComplete sc = new StrainComplete(name, strain);
                    datos.add(sc);
                }

                containsEffect = false;

            }
        }

        if (filterType.equals("byRace")) {

            for(Map.Entry<String, Strain> entry : result.entrySet()) {

                String name = entry.getKey();
                Strain strain = entry.getValue();

                if (strain.getRace().equals(race.toLowerCase())) {
                    StrainComplete sc = new StrainComplete(name, strain);
                    datos.add(sc);
                }

            }
        }

        if (filterType.equals("byEffect")) {

            for(Map.Entry<String, Strain> entry : result.entrySet()) {

                String name = entry.getKey();
                Strain strain = entry.getValue();

                if (strain.getEffects().getMedical().contains(effect) ||
                        strain.getEffects().getNegative().contains(effect) ||
                        strain.getEffects().getPositive().contains(effect)) {

                    StrainComplete sc = new StrainComplete(name, strain);
                    datos.add(sc);

                }

            }
        }

        if (filterType.equals("byFlavor")) {



            for(Map.Entry<String, Strain> entry : result.entrySet()) {

                String name = entry.getKey();
                Strain strain = entry.getValue();

                if (strain.getFlavors().contains(flavor)) {
                    StrainComplete sc = new StrainComplete(name, strain);
                    datos.add(sc);
                }

            }
        }



        myRecycler = findViewById(R.id.myRecycler);

        ResultAdapter adapter = new ResultAdapter(datos);
        adapter.setListener(Results.this);
        myRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);



    }

    @Override
    public void onFailure(Call<Map<String, Strain>> call, Throwable t) {
        Toast.makeText(this, "Error de conexión con el servidor", Toast.LENGTH_LONG).show();
    }
}
