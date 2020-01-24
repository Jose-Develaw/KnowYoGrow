package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Filter extends AppCompatActivity implements Callback<Map<String, Strain>>, View.OnClickListener, Serializable {

    Map<String, Strain> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Button getButton = (Button) findViewById(R.id.getButton);
        getButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        Call<Map<String, Strain>> resultados = ApiService.getApiService().getAll();
        resultados.enqueue(this);
        System.out.println("Loquesea");
        //Intent i = new Intent(Filter.this, Results.class);
        //startActivity(i);
    }

    @Override
    public void onResponse(Call<Map<String, Strain>> call, Response<Map<String, Strain>> response) {

        Map<String, Strain> result = response.body();
        Toast.makeText(this, "De puta madre", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Call<Map<String, Strain>> call, Throwable t) {
        Toast.makeText(this, "Error de conexión con el servidor", Toast.LENGTH_LONG).show();
    }
}
