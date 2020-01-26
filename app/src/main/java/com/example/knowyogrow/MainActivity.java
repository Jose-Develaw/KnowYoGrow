package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ResultAdapter.Listener {

    DBInterface dbInterface;
    StrainIntermediate si;
    ArrayList<StrainComplete> data;
    RecyclerView favouriteRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        favouriteRecycler = findViewById(R.id.favouriteRecycler);
        setSupportActionBar(toolbar);
        loadingData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public void filterByRace(MenuItem item) {
        Intent intent = new Intent(this, FilterByRace.class);
        startActivity(intent);
    }

    public void filterByEffect(MenuItem item) {
        Intent intent = new Intent(this, FilterByEffect.class);
        startActivity(intent);
    }

    public void filterByFlavor(MenuItem item) {
        Intent intent = new Intent(this, FilterByFlavor.class);
        startActivity(intent);
    }

    public void filterByAll(MenuItem item) {
        Intent intent = new Intent(this, Filter.class);
        startActivity(intent);
    }

    public void loadingData() {
        si = null;
        data = new ArrayList<>();
        dbInterface = new DBInterface(MainActivity.this);
        dbInterface.open();
        Cursor intermedio = dbInterface.getStrainT();
        if (intermedio.moveToFirst() && intermedio != null) {
            do {
                ArrayList<String> flavors = new ArrayList<>();
                ArrayList<String> positive_fx = new ArrayList<>();
                ArrayList<String> negative_fx = new ArrayList<>();
                ArrayList<String> medical_fx = new ArrayList<>();
                si = new StrainIntermediate(intermedio.getInt(0), intermedio.getString(1), intermedio.getString(2));

                Cursor flavorsC = dbInterface.getFlavorT(si.getId());
                if (flavorsC.moveToFirst() && flavorsC != null) {
                    do {
                        flavors.add(flavorsC.getString(0));
                    } while (flavorsC.moveToNext());
                }

                Cursor positiveC = dbInterface.getPositiveT(si.getId());
                if (positiveC.moveToFirst() && positiveC != null) {
                    do {
                        positive_fx.add(positiveC.getString(0));
                    } while (positiveC.moveToNext());
                }

                Cursor negativeC = dbInterface.getNegativeT(si.getId());
                if (negativeC.moveToFirst() && negativeC != null) {
                    do {
                        negative_fx.add(negativeC.getString(0));
                    } while (negativeC.moveToNext());
                }

                Cursor medicalC = dbInterface.getMedicalT(si.getId());
                if (medicalC.moveToFirst() && medicalC != null) {
                    do {
                        medical_fx.add(medicalC.getString(0));
                    } while (medicalC.moveToNext());
                }

                Strain strain = new Strain(si.getId(), si.getRace(), flavors, new Effects(positive_fx, negative_fx, medical_fx));
                StrainComplete sc = new StrainComplete(si.getName(), strain);
                data.add(sc);

            } while (intermedio.moveToNext());


        }

        /*Call<Map<String, Strain>> resultados = ApiService.getApiService().getAll();
        resultados.enqueue(new Callback<Map<String, Strain>>() {
            @Override
            public void onResponse(Call<Map<String, Strain>> call, Response<Map<String, Strain>> response) {
                data = new ArrayList<>();

                dbInterface = new DBInterface(MainActivity.this);
                dbInterface.open();


                Cursor c = dbInterface.getFavourites();
                int [] numberOfFavs = new int[c.getCount()];
                int counter = 0;

                data.clear();

                if (c.moveToFirst() && c != null) {
                    do {
                        numberOfFavs[counter] = c.getInt(0);
                        counter++;
                    } while (c.moveToNext());
                }

                Map<String, Strain> result = response.body();
                for(Map.Entry<String, Strain> entry : result.entrySet()) {

                    String name = entry.getKey();
                    Strain strain = entry.getValue();

                    for(int i = 0; i < numberOfFavs.length; i++) {
                        if(strain.getId() == numberOfFavs[i]) {
                            StrainComplete sc = new StrainComplete(name, strain);
                            data.add(sc);
                        }
                    }

                }

                ResultAdapter adapter = new ResultAdapter(data);
                adapter.setListener(MainActivity.this);
                favouriteRecycler.setAdapter(adapter);
                favouriteRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Map<String, Strain>> call, Throwable t) {

            }
        });*/



        ResultAdapter adapter = new ResultAdapter(data);
        adapter.setListener(MainActivity.this);
        favouriteRecycler.setAdapter(adapter);
        favouriteRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onResultClick(int position) {
        StrainComplete sc = data.get(position);
        Intent i = new Intent(MainActivity.this, Detail.class);
        i.putExtra("strainClicked", sc);
        startActivity(i);
    }
}
