package com.example.knowyogrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Checker {

    public static void check(Context context) {

        try {
            SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("preferences", 0);
            String lastAccessString = sharedPreferences.getString("lastAccess", null);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm aa");
            Date date = Calendar.getInstance().getTime();
            String currentDate = sdf.format(date);
            Date lastAccess = sdf.parse(lastAccessString);
            long diffInMillies = Math.abs(date.getTime() - lastAccess.getTime());
            if (diffInMillies > 60000L) {

                Toast.makeText (context, "Your session has expired", Toast.LENGTH_LONG).show ();
                Intent i = new Intent(context, Login.class);
                context.startActivity(i);

            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lastAccess", currentDate);
                editor.commit();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

}
