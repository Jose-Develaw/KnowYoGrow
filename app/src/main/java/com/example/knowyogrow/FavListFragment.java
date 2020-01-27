package com.example.knowyogrow;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavListFragment extends Fragment implements  ResultAdapter.IOnLongClick, ResultAdapter.Listener {

    DBInterface dbInterface;
    StrainIntermediate si;
    ArrayList<StrainComplete> data;
    ResultAdapter adapter;
    int position = -1;

    public FavListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fav_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadingData();
        adapter = new ResultAdapter(data);
        adapter.setListener(this);
        adapter.setiOnLongClick(this);
        RecyclerView favouriteRecycler = view.findViewById(R.id.recyclerFavList);
        favouriteRecycler.setAdapter(adapter);
        favouriteRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResultClick(int position) {
        StrainComplete sc = data.get(position);
        Intent i = new Intent(getLayoutInflater().getContext(), Detail.class);
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
                Toast.makeText(getLayoutInflater().getContext(), "Strain '"+data.get(position).getName()+"' has been removed from favs",
                        Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                adapter.remove(position);
                if (data.size() == 0) {
                    Intent intent = getActivity().getIntent();
                    getActivity().overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(intent);
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void loadingData() {

        si = null;
        data = new ArrayList<>();
        dbInterface = new DBInterface(getLayoutInflater().getContext());
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

    }

}
