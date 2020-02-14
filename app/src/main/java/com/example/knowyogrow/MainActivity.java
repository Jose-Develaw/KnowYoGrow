package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity /*implements ResultAdapter.Listener, ResultAdapter.IOnLongClick */{

    DBInterface dbInterface;
    StrainIntermediate si;
    FrameLayout frameMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        frameMain = (FrameLayout) findViewById(R.id.frameMain);
        setSupportActionBar(toolbar);
        loadingFragment();
        //((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadingFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);


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

    public void logOut(MenuItem item) {

        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void loadingFragment() {

        si = null;

        dbInterface = new DBInterface(MainActivity.this);
        dbInterface.open();
        Cursor intermedio = dbInterface.getStrainT();
        if (intermedio.moveToFirst() && intermedio != null) {

            FavListFragment flf = new FavListFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(frameMain.getId(), flf);
            ft.commitNow();

        } else {

            EmptyListFragment elf = new EmptyListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace (frameMain.getId(), elf);
            ft.commitNow();

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");

            if (getIntent().getStringExtra("fromResults") == null) {

                onBackPressed();
            }


        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
        return;
    }



}
