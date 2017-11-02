package com.ares.pulkit.studyease;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Pulkit on 25-03-2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DB_name="STUDY";
    private static final int DB_version=3;
    SQLiteDatabase db;
    Database(Context context) {
        super(context,DB_name,null,DB_version);

    }
    public void onCreate(SQLiteDatabase db) {
    //    db.execSQL("CREATE TABLE add1 ("+"id INTEGER PRIMARY KEY AUTOINCREMENT "+" );");

        //  Cursor cursor=db.query("STUDENT",new String[]{"name","surname"},null,null,null,null,null);
    }
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion)
    {
        db.execSQL("DROP TABLE IF EXISTS sqlite_sequence");
        onCreate(db);
    }

    public void  addit(String topic)
    {   SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE "+topic+"("+"  image BLOB );");

    }
    public void delete(String d_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+d_name);

    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
