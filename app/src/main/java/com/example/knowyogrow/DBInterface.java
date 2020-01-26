package com.example.knowyogrow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBInterface {

    //Table Strain
    public static final String BD_STRAIN_T = "strain_t";
    public static final String STRAIN_T_ID = "id";
    public static final String STRAIN_T_NAME = "name";
    public static final String STRAIN_T_RACE = "race";

    //Table Flavors
    public static final String BD_FLAVORS_T = "flavors_t";
    public static final String FLAVORS_T_STRAIN_ID = "strain_id";
    public static final String FLAVORS_T_FLAVOR = "strain_flavor";

    //Table Positive FX
    public static final String BD_POSITIVE_T = "positive_t";

    public static final String POSITIVE_T_STRAIN_ID = "strain_id";
    public static final String POSITIVE_T_FX = "strain_positive_fx";

    //Table Negative FX
    public static final String BD_NEGATIVE_T = "negative_t";
    public static final String NEGATIVE_T_STRAIN_ID = "strain_id";
    public static final String NEGATIVE_T_FX = "strain_negative_fx";

    //Table Medical FX
    public static final String BD_MEDICAL_T = "medical_t";
    public static final String MEDICAL_T_STRAIN_ID = "strain_id";
    public static final String MEDICAL_T_FX = "strain_medical_fx";


    // Constantes
    public static final String CAMPO_ID = "_id";
    public static final String TAG = "DBInterface";
    public static final String BD_NOMBRE = "favouritesDB";
    public static final String BD_TABLA = "favouritesT";
    public static final int VERSION = 1;

    public static final String BD_CREATE_STRAIN = "create table " + BD_STRAIN_T + "(" + STRAIN_T_ID + " integer not null, " + STRAIN_T_NAME + " text not null, " + STRAIN_T_RACE + " text not null);";
    public static final String BD_CREATE_FLAVORS =  "create table " + BD_FLAVORS_T + "(" + FLAVORS_T_STRAIN_ID + " integer not null, " + FLAVORS_T_FLAVOR + " text not null);";
    public static final String BD_CREATE_POSITIVE =  "create table " + BD_POSITIVE_T + "(" + POSITIVE_T_STRAIN_ID + " integer not null, " + POSITIVE_T_FX + " text not null);";
    public static final String BD_CREATE_NEGATIVE =  "create table " + BD_NEGATIVE_T + "(" + NEGATIVE_T_STRAIN_ID + " integer not null, " + NEGATIVE_T_FX + " text not null);";
    public static final String BD_CREATE_MEDICAL=  "create table " + BD_MEDICAL_T + "(" + MEDICAL_T_STRAIN_ID + " integer not null, " + MEDICAL_T_FX + " text not null);";


           /* "create table " + BD_TABLA + "(" + CAMPO_ID +
                    " integer); ";*/

    private final Context contexto;
    private AyudaDB ayuda;
    private SQLiteDatabase bd;

    public DBInterface (Context con)
    {
        this.contexto = con;
        Log.w(TAG, "creating help" );
        ayuda = new AyudaDB(contexto);
    }

    public DBInterface open () throws SQLException {
        Log.w(TAG, "opening database" );
        bd = ayuda.getWritableDatabase();
        return this;
    }

    // Cierra la BD
    public void close () {
        ayuda.close();
    }

    public long newFavourite(int id, String name, String race, ArrayList<String> flavors, ArrayList<String> positive_fx, ArrayList<String> negative_fx, ArrayList<String> medical_fx)
    {
        int result = 5;

        try{
            ContentValues initialValues = new ContentValues ();
            initialValues.put(STRAIN_T_ID, id);
            initialValues.put(STRAIN_T_NAME, name);
            initialValues.put(STRAIN_T_RACE, race);
            bd.insert(BD_STRAIN_T, null,
                    initialValues);


            for (String flavor : flavors) {
                initialValues = new ContentValues ();
                initialValues.put(FLAVORS_T_STRAIN_ID, id);
                initialValues.put(FLAVORS_T_FLAVOR, flavor);
                bd.insert(BD_FLAVORS_T, null,
                        initialValues);
            }

            for (String positive : positive_fx) {
                initialValues = new ContentValues ();
                initialValues.put(POSITIVE_T_STRAIN_ID, id);
                initialValues.put(POSITIVE_T_FX, positive);
                bd.insert(BD_POSITIVE_T, null,
                        initialValues);
            }

            for (String negative : negative_fx) {
                initialValues = new ContentValues ();
                initialValues.put(NEGATIVE_T_STRAIN_ID, id);
                initialValues.put(NEGATIVE_T_FX, negative);
                bd.insert(BD_NEGATIVE_T, null,
                        initialValues);
            }

            for (String medical : medical_fx) {
                initialValues = new ContentValues ();
                initialValues.put(MEDICAL_T_STRAIN_ID, id);
                initialValues.put(MEDICAL_T_FX, medical);
                bd.insert(BD_MEDICAL_T, null,
                        initialValues);
            }

            result = 0;
            return 0;

        } catch (Exception e) {
            result = 1;
            return 1;
        }
    }



    public Cursor getStrainT(){
        return bd.query(BD_STRAIN_T, new String []
                        {STRAIN_T_ID, STRAIN_T_NAME, STRAIN_T_RACE},
                null,null, null, null,
                null);
    }

    public Cursor getFlavorT(int id){

        return bd.query(BD_FLAVORS_T, new String []
                        {FLAVORS_T_FLAVOR},
                FLAVORS_T_STRAIN_ID + "=" + id, null, null, null,
                null);

    }
    public Cursor getPositiveT(int id){

        return bd.query(BD_POSITIVE_T, new String []
                        {POSITIVE_T_FX},
                POSITIVE_T_STRAIN_ID + "=" + id, null, null, null,
                null);
    }
    public Cursor getNegativeT(int id){

        return bd.query(BD_NEGATIVE_T, new String []
                        {NEGATIVE_T_FX},
                NEGATIVE_T_STRAIN_ID + "=" + id, null, null, null,
                null);
    }
    public Cursor getMedicalT(int id){

        return bd.query(BD_MEDICAL_T, new String []
                        {MEDICAL_T_FX},
                MEDICAL_T_STRAIN_ID + "=" + id, null, null, null,
                null);
    }


    public long deleteFavourite(long id)
    {
        bd.delete(BD_STRAIN_T, STRAIN_T_ID + "=" + id, null);
        bd.delete(BD_FLAVORS_T, FLAVORS_T_STRAIN_ID + "=" + id, null);
        bd.delete(BD_POSITIVE_T, POSITIVE_T_STRAIN_ID + "=" + id, null);
        bd.delete(BD_NEGATIVE_T, NEGATIVE_T_STRAIN_ID + "=" + id, null);
        bd.delete(BD_MEDICAL_T, MEDICAL_T_STRAIN_ID + "=" + id, null);
        return 1;
    }

    public class AyudaDB extends SQLiteOpenHelper {

        public AyudaDB(Context con){
            super (con, BD_NOMBRE, null, VERSION);
            Log.w(TAG, "constructor de ayuda");
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.w(TAG, "creando la base de datos "+BD_CREATE_STRAIN );
                db.execSQL(BD_CREATE_STRAIN);
                Log.w(TAG, "creando la base de datos "+BD_CREATE_FLAVORS );
                db.execSQL(BD_CREATE_FLAVORS);
                Log.w(TAG, "creando la base de datos "+BD_CREATE_POSITIVE );
                db.execSQL(BD_CREATE_POSITIVE);
                Log.w(TAG, "creando la base de datos "+BD_CREATE_NEGATIVE );
                db.execSQL(BD_CREATE_NEGATIVE);
                Log.w(TAG, "creando la base de datos "+BD_CREATE_MEDICAL );
                db.execSQL(BD_CREATE_MEDICAL);
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }
        @Override
        public void onUpgrade (SQLiteDatabase db,
                               int VersionAntigua, int VersionNueva) {
            Log.w(TAG, "Actualizando Base de datos de la versión" +
                    VersionAntigua + "A" + VersionNueva + ". Destruirá todos los datos");
            db.execSQL("DROP TABLE IF EXISTS " + BD_TABLA);
            onCreate(db);
        }
    }
}

