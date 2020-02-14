package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button enter = (Button) findViewById(R.id.buttonEnter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                i.putExtra("finisher", new ResultReceiver(null) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        Login.this.finish();
                    }
                });
                startActivityForResult(i,1);
                //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //finish();
            }
        });


    }
}
