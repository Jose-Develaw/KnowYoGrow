package com.example.knowyogrow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        Button enter = (Button) findViewById(R.id.buttonEnter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                Date date = Calendar.getInstance().getTime();
                String currentDate = sdf.format(date);
                String boomTime = "02/16/2050 11:59 PM ";

                //if (currentDate.compareTo(boomTime) < 0) {

                     if (checkConnectivity()) {

                        login();

                         } else {

                         Toast.makeText (Login.this, "Network not available",
                                Toast.LENGTH_LONG).show ();
                         }

                //} else {

                    //Toast.makeText(Login.this, "Access denied. Your trial period has expired", Toast.LENGTH_LONG).show();
                //}



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

    public void login() {

        LoginData loginData = new LoginData(user.getText().toString(), password.getText().toString());
        Call<Acceso> accesoCall = LoginApiService.getApiService().tryLogin(loginData);
        accesoCall.enqueue(new Callback<Acceso>() {
            @Override
            public void onResponse(Call<Acceso> call, Response<Acceso> response) {

                Acceso respuesta = (Acceso) response.body();
                if (respuesta.access.equals("Granted")){

                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    i.putExtra("finisher", new ResultReceiver(null) {
                        @Override
                        protected void onReceiveResult(int resultCode, Bundle resultData) {
                            Login.this.finish();
                        }
                    });

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
                    Date date = Calendar.getInstance().getTime();
                    String currentDate = sdf.format(date);
                    SharedPreferences sharedPreferences = getSharedPreferences("preferences", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastAccess", currentDate);
                    editor.commit();

                    startActivityForResult(i,1);


                } else {

                    Toast.makeText (Login.this, "User and password fields don't match any registered user. Please, try again",
                            Toast.LENGTH_LONG).show ();
                }
            }

            @Override
            public void onFailure(Call<Acceso> call, Throwable t) {

                Toast.makeText (Login.this, "Server error",
                        Toast.LENGTH_LONG).show ();
            }
        });
    }
}
