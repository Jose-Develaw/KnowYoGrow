package com.example.knowyogrow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBInterface {

    // Constantes
    public static final String CAMPO_ID = "_id";
    public static final String TAG = "DBInterface";

    public static final String BD_NOMBRE = "favouritesDB";
    public static final String BD_TABLA = "favouritesT";
    public static final int VERSION = 1;

    public static final String BD_CREATE =
            "create table " + BD_TABLA + "(" + CAMPO_ID +
                    " integer); ";

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

    public long newFavourite(int id)
    {
        ContentValues initialValues = new ContentValues ();
        initialValues.put(CAMPO_ID, id);
        return bd.insert(BD_TABLA, null,
                initialValues);
    }

    // Devuelve todos los Periodicos
    public Cursor getFavourites(){
        return bd.query(BD_TABLA, new String []
                        {CAMPO_ID},
                null,null, null, null,
                null);
    }



    public long deleteFavourite(long id)
    {
        return bd.delete(BD_TABLA, CAMPO_ID + "=" + id, null);
    }

    public class AyudaDB extends SQLiteOpenHelper {

        public AyudaDB(Context con){
            super (con, BD_NOMBRE, null, VERSION);
            Log.w(TAG, "constructor de ayuda");
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.w(TAG, "creando la base de datos "+BD_CREATE );
                db.execSQL(BD_CREATE);
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

