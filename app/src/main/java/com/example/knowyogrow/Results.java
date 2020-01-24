package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Results extends AppCompatActivity implements ResultAdapter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayList<Result> datos = new ArrayList<>();

        Result r1 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Ak-47", "Hybrid");
        Result r2 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "White Widow", "Hybrid");
        Result r3 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Somango", "Sativa");
        Result r4 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Nébula", "Indica");
        Result r5 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Ak-47", "Hybrid");
        Result r6 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "White Widow", "Hybrid");
        Result r7 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Somango", "Sativa");
        Result r8 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Nébula", "Indica");
        Result r9 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Ak-47", "Hybrid");
        Result r10 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "White Widow", "Hybrid");
        Result r11 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Somango", "Sativa");
        Result r12 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Nébula", "Indica");
        Result r13 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Ak-47", "Hybrid");
        Result r14= new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "White Widow", "Hybrid");
        Result r15 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Somango", "Sativa");
        Result r16 = new Result("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg", "Nébula", "Indica");

        datos.add(r1);
        datos.add(r2);
        datos.add(r3);
        datos.add(r4);
        datos.add(r5);
        datos.add(r6);
        datos.add(r7);
        datos.add(r8);
        datos.add(r9);
        datos.add(r10);
        datos.add(r11);
        datos.add(r12);
        datos.add(r13);
        datos.add(r14);
        datos.add(r15);
        datos.add(r16);

        RecyclerView myRecycler = findViewById(R.id.myRecycler);
        myRecycler.setLayoutManager( new LinearLayoutManager(this));
        ResultAdapter adapter = new ResultAdapter(datos);
        adapter.setListener(Results.this);
        myRecycler.setAdapter(adapter);

    }

    @Override
    public void onResultClick() {
        Intent i = new Intent(Results.this, Detail.class);
        startActivity(i);
    }
}
