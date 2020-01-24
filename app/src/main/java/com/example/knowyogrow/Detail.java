package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageDetail = (ImageView) findViewById(R.id.imageDetail);
        Picasso.get().load("https://cdn.pixabay.com/photo/2018/01/06/18/54/marijuana-3065611_960_720.jpg").transform(new ImageRoundCorners()).resize(350, 350).into(imageDetail);
    }
}
