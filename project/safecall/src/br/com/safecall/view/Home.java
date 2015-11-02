/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.view;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.safecall.control.Controller;
import br.com.safecall.control.Location;
import br.com.safecall.control.to.Configuration;

/**
 *
 * @author User
 */
public class Home {

    private Activity activity;
    private CountDownTimer cron;
    private Location location;
    private boolean cronActive;
    private Controller controller;

    public Home(final Activity activity, Controller controller) {
        this.location = new Location(activity);
        this.activity = activity;
        this.controller = controller;
    }

    public Configuration getConfig() {
        return controller.getConfig();
    }

    public void updateCron() {
        cron = new CountDownTimer(controller.getConfig().getSeconds() * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                String hh = String.format("%02d", (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24));
                String mm = String.format("%02d", (millisUntilFinished / 60000) % 60);
                String ss = (String.format("%02d", (millisUntilFinished % 60000 / 1000)));
                ((TextView) getActivity().findViewById(R.id.timer)).setText(hh + ":" + mm + ":" + ss);
            }

            public void onFinish() {
                cronActive = false;
                ((TextView) getActivity().findViewById(R.id.timer)).setText("00:00:00");
                ((ImageView) getActivity().findViewById(R.id.safe)).setImageResource(R.drawable.sign_out);
                String message = controller.getConfig().getMessage();
                String loc = location.getLocation();
                if (loc != null) {
                    message += "\n" + loc;
                }
                for (String col : controller.getConfig().getTelephones()) {
                    controller.sendSms(col.toString(), message);
                }
            }
        };
    }

    /**
     * @return the cron
     */
    public CountDownTimer getCron() {
        return cron;
    }

    /**
     * @param cron the cron to set
     */
    public void setCron(CountDownTimer cron) {
        this.cron = cron;
    }

    /**
     * @return the cronActive
     */
    public boolean isCronActive() {
        return cronActive;
    }

    /**
     * @param cronActive the cronActive to set
     */
    public void setCronActive(boolean cronActive) {
        this.cronActive = cronActive;
    }

    /**
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
