/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.dao;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.safecall.control.to.Configuration;
import br.com.safecall.control.to.ReportItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author User
 */
public class DAO {

    public void setMessage(String message) {
        ContentValues values = new ContentValues();
        values.put("MESSAGE", message);
        DatabaseManager.getInstance().openDatabase().update("CONFIG", values, null,
                null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void setMinutes(Long minutes) {
        ContentValues values = new ContentValues();
        values.put("MINUTES", minutes);
        DatabaseManager.getInstance().openDatabase().update("CONFIG", values, null,
                null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void setTelephones(ArrayList<String> telephones) {
        String tels = "";
        for (int i = 0; i < telephones.size(); i++) {
            tels += telephones.get(i);
            if ((i < telephones.size() - 1)) {
                tels += ";";
            }

        }
        ContentValues values = new ContentValues();
        values.put("TELEPHONES", tels);
        DatabaseManager.getInstance().openDatabase().update("CONFIG", values, null,
                null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void insertIntoHistory(String message, String status, String telephone, boolean sent) {
        ContentValues values = new ContentValues();
        values.put("CONTENT", message);
        values.put("TELEPHONE", telephone);
        values.put("SENT", sent == true ? 1 : 0);
        values.put("STATUS", status);
        values.put("MESSAGE_TIMESTAMP", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
        DatabaseManager.getInstance().openDatabase().insert("HISTORY", null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void firstConfig() {
        ContentValues values = new ContentValues();
        values.put("MESSAGE", "");
        values.put("MINUTES", 0);
        values.put("TELEPHONES", "");
        DatabaseManager.getInstance().openDatabase().insert("CONFIG", null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public Configuration getCurrentConfig() {
        Configuration config = null;
        String sqlQuery = "SELECT MESSAGE, MINUTES, TELEPHONES FROM CONFIG";
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            config = new Configuration();
            do {
                config.setMessage(cursor.getString(0));
                config.setSeconds(cursor.getLong(1) * 60);
                String tels[] = cursor.getString(2).split(";");
                for (String tel : tels) {
                    if (!tel.trim().isEmpty()) {
                        config.getTelephones().add(tel.trim());
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return config;

    }

    public ArrayList<ReportItem> getReports() {
        ArrayList<ReportItem> report = new ArrayList<ReportItem>();
        String sqlQuery = "SELECT CONTENT, SENT, MESSAGE_TIMESTAMP, TELEPHONE, STATUS FROM HISTORY";
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {

            do {
                ReportItem item = new ReportItem();
                item.setMessage(cursor.getString(0));
                item.setSent(cursor.getInt(1) == 1);
                item.setDate(cursor.getString(2));
                item.setTelephone(cursor.getString(3));
                item.setStatus(cursor.getString(4));
                report.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return report;
    }
}
