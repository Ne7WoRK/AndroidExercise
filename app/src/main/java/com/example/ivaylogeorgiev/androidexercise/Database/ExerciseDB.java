package com.example.ivaylogeorgiev.androidexercise.Database;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivaylogeorgiev on 04/05/2019.
 */

public class ExerciseDB extends SQLiteAssetHelper{

    private static final String DB_Name = "ExerciseDB.db";
    private static final int DB_VER=1;


    //Constructor of DB class.
    public ExerciseDB(Context context) {

        super(context, DB_Name,null,DB_VER);
    }



    /**
     * This method will be used to query the database and return the integer value representing the
     * difficulty mode stored in database.
     */

    public int getDatabaseDifficulty(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Mode"};
        String sqlTable = "Setting";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Mode"));
    }




    //This method will be used to save/update the difficulty (integer) stored in database.

    public void saveDifficulty(int value){

        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE Setting SET Mode = " + value;
        db.execSQL(query);
    }



    /**
     * This method will be used to query the database and obtain list of strings (longs)
     * which will be converted to days. The obtained list of strings (longs) will represent
     * all days that a user performed a training.
     */
    public List<String> getWorkoutDays(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Day"};
        String sqlTable = "WorkoutDays";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        List<String> result = new ArrayList<>();

        if(c.moveToFirst()){

            do{

                result.add(c.getString(c.getColumnIndex("Day")));

            } while (c.moveToNext());
        }

        return result;
    }

    /**
     * This method will take a long number in a string format representing current day and time
     * and save it on SQLite database. Each record stored in database will represent a day in string (long) format.
     */

    public void saveWorkoutDay(String value){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO WorkoutDays(Day) VALUES ('%s');", value);
        db.execSQL(query);
    }
}
