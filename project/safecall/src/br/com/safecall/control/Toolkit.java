/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.control;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 *
 * @author User
 */
public class Toolkit {

    public static final String ACTION_SMS_SENT = "SMS_SENT";
    public static final String ACTION_SMS_DELIVERED = "SMS_DELIVERED";

    void sendSMS(Activity activity, String tel, String text) {
        Intent sendIntent = new Intent(ACTION_SMS_SENT);
         SmsManager sms = SmsManager.getDefault();
         sendIntent.putExtra("tel", tel);
         sendIntent.putExtra("message", text);
         PendingIntent send = PendingIntent.getBroadcast(activity, 0, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
         PendingIntent deliver = PendingIntent.getBroadcast(activity, 0, new Intent(ACTION_SMS_DELIVERED), PendingIntent.FLAG_UPDATE_CURRENT);
         sms.sendTextMessage(tel, null, "[SafeCall] " + text, send, deliver);
         
    }
}
