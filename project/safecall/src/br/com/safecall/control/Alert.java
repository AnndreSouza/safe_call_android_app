package br.com.safecall.control;


import android.app.AlertDialog;
import android.content.Context;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Alert {

    public AlertDialog getAlert(Context c, int titleId, int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        return builder.create();
    }

    public AlertDialog getAlert(Context c, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder.create();
    }
}
