/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author User
 */
public class DatabaseManager {

    private int counter;

    private static DatabaseManager databaseManager;
    private static SQLiteOpenHelper helper;
    private SQLiteDatabase database;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
            DatabaseManager.helper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        return databaseManager;
    }

    public synchronized SQLiteDatabase openDatabase() {
        counter++;
        if (counter == 1) {
            database = helper.getWritableDatabase();
        }
        return database;
    }

    public synchronized void closeDatabase() {
        counter--;
        if (counter == 0) {
            database.close();

        }
    }
}