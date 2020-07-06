package com.example.taskfour.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Level extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Level.db";
    public static final String TABLE_NAME = "level_info";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "VALUE";
    public static long result;

    public Level( @Nullable Context context ) {
        super ( context, DATABASE_NAME, null, 1 );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL ( "create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,VALUE TEXT)" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL ( "DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate ( db );
    }

    public boolean Insert(String name ){
        SQLiteDatabase db = this.getWritableDatabase ();

        ContentValues values = new ContentValues (  );
        values.put ( COL_2,name );
        result = db.insert ( TABLE_NAME,null,values );

        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor FetchData(){
        SQLiteDatabase db = this.getWritableDatabase ();
        Cursor res = db.rawQuery ( "select * from "+TABLE_NAME,null );
        return res;
    }

    public Integer DeleteData (String name){
        SQLiteDatabase db = this.getWritableDatabase ();
        return db.delete ( TABLE_NAME,"VALUE = ?",new String[] {name} );
    }


    public void update( int id, String value){
        SQLiteDatabase db = this.getWritableDatabase ();
        ContentValues contentValues = new ContentValues (  );
        contentValues.put ( "VALUE", value );
        db.update ( TABLE_NAME,contentValues,"ID="+String.valueOf ( id ),null );
    }
}
