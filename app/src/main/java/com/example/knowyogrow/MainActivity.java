package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
}
