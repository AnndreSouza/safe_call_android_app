/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.control;

import android.app.Activity;
import br.com.safecall.control.to.Configuration;
import br.com.safecall.control.to.ReportItem;
import br.com.safecall.dao.DAO;
import br.com.safecall.dao.DatabaseHandler;
import br.com.safecall.dao.DatabaseManager;
import br.com.safecall.view.MainActivity;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Controller {

    private MainActivity activity;
    private DAO dao;

    public ArrayList<ReportItem> getReports() {
        return dao.getReports();
    }

    public Controller(Activity activity) {
        DatabaseManager.initializeInstance(new DatabaseHandler(activity));
        this.activity = (MainActivity) activity;
        dao = new DAO();
    }
    private Toolkit t = new Toolkit();
    private Configuration config;

    public void sendSms(String tel, String message) {
        try {
            t.sendSMS(activity, tel, message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        Configuration con = dao.getCurrentConfig();
        if (con == null) {
            dao.firstConfig();
            con = dao.getCurrentConfig();
        }
        config = con;
        return config;
    }

    public void setMinutes(Long minutes) {
        dao.setMinutes(minutes);
    }

    public void setTelephones(ArrayList<String> tel) {
        dao.setTelephones(tel);
    }

    public void setMessage(String mess) {
        dao.setMessage(mess);
    }

    public void updateMainActiviryUI() {
        activity.updateUI();
    }

    public void insertIntoHistory(String message, String result, String tel, boolean sent) {
        dao.insertIntoHistory(message, result, tel, sent);
    }
}
