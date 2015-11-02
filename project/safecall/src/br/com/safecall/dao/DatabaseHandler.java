/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String database = "safeCall";
    private ArrayList<String> sql;

    public DatabaseHandler(Context context) {
        super(context, database, null, DATABASE_VERSION);
        sql = new ArrayList<String>();
        sql.add("CREATE TABLE HISTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT, CONTENT TEXT, "
                + "TELEPHONE TEXT, STATUS TEXT, SENT INTEGER, "
                + "MESSAGE_TIMESTAMP TEXT)");
        sql.add("CREATE TABLE CONFIG (MINUTES INTEGER, MESSAGE TEXT, TELEPHONES TEXT)");
    }

    public void setSql(ArrayList<String> sql) {
        this.sql = sql;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sql1 : sql) {
            db.execSQL(sql1);
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS HISTORY");
        db.execSQL("DROP TABLE IF EXISTS CONFIG");
        // Create tables again
        onCreate(db);
    }

}
