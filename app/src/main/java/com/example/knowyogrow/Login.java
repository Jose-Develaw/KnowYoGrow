package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button enter = (Button) findViewById(R.id.buttonEnter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                Date date = Calendar.getInstance().getTime();
                String currentDate = sdf.format(date);
                String boomTime = "02/17/2020 09:45 PM ";

                if (currentDate.compareTo(boomTime) < 0) {

                     if (checkConnectivity()) {

                         Intent i = new Intent(Login.this, MainActivity.class);
                         startActivity(i);
                         i.putExtra("finisher", new ResultReceiver(null) {
                             @Override
                             protected void onReceiveResult(int resultCode, Bundle resultData) {
                                 Login.this.finish();
                             }
                         });
                         startActivityForResult(i,1);

                         } else {

                         Toast.makeText (Login.this, "Network not available",
                                Toast.LENGTH_LONG).show ();
                         }

                } else {

                    Toast.makeText(Login.this, "Access denied. Your trial period has expired", Toast.LENGTH_LONG).show();
                }



            }
        });


    }

    public boolean checkConnectivity() {

        // Obtenemos un gestor de las conexiones de red
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService (this.CONNECTIVITY_SERVICE);

        // Obtenemos el estado de la red
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Si está conectado
        if (networkInfo != null && networkInfo.isConnected ()) {

            return true;

        } else {

            return false;
        }


    }
}
