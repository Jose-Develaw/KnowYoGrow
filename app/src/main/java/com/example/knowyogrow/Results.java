package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        race = i.getStringExtra("race");
        effect = i.getStringExtra("effect");
        flavor = i.getStringExtra("flavor");
        datos = new ArrayList<>();
        setContentView(R.layout.activity_results);
        myRecycler = findViewById(R.id.myRecycler);
        myRecycler.setLayoutManager( new LinearLayoutManager(this));
        Call<Map<String, Strain>> resultados = ApiService.getApiService().getAll();
        resultados.enqueue(this);

    }

    @Override
    public void onResultClick(int position) {
        StrainComplete sc = datos.get(position);
        Intent i = new Intent(Results.this, Detail.class);
        i.putExtra("strainClicked", sc);
        startActivity(i);
    }

    @Override
    public void onResponse(Call<Map<String, Strain>> call, Response<Map<String, Strain>> response) {

        Map<String, Strain> result = response.body();
        boolean containsEffect = false;
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

        myRecycler = findViewById(R.id.myRecycler);

        ResultAdapter adapter = new ResultAdapter(datos);
        adapter.setListener(Results.this);
        myRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onFailure(Call<Map<String, Strain>> call, Throwable t) {
        Toast.makeText(this, "Error de conexión con el servidor", Toast.LENGTH_LONG).show();
    }
}
