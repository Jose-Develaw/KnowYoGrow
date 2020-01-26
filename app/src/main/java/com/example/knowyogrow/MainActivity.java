package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ResultAdapter.Listener, ResultAdapter.IOnLongClick {

    DBInterface dbInterface;
    StrainIntermediate si;
    ArrayList<StrainComplete> data;
    RecyclerView favouriteRecycler;
    ResultAdapter adapter;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        favouriteRecycler = findViewById(R.id.favouriteRecycler);
        adapter = new ResultAdapter(data);
        adapter.setListener(MainActivity.this);
        adapter.setiOnLongClick(this);
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

    @Override
    public void showMenu(int position , View view) {
        registerForContextMenu(view);
        this.position = position;
        view.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        Activity activity= (Activity) v.getContext();
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.main_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.deleteFromFavs:
                dbInterface.deleteFavourite(data.get(position).getStrain().getId());
                Toast.makeText(this, "Strain '"+data.get(position).getName()+"' has been removed from favs",
                        Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                adapter.remove(position);
                break;


        }
        return super.onContextItemSelected(item);
    }
}
