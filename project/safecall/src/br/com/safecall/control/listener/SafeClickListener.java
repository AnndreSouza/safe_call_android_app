/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.control.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import br.com.safecall.control.Alert;
import br.com.safecall.control.Controller;
import br.com.safecall.control.to.Configuration;
import br.com.safecall.view.Home;
import br.com.safecall.view.R;

/**
 *
 * @author User
 */
public class SafeClickListener implements OnClickListener {

    public boolean click = false;
    private Home home;

    public SafeClickListener(Home home) {
        this.home = home;
    }

    public void onClick(View v) {
        Configuration con = home.getConfig();
        if (con.getMessage().isEmpty() || con.getSeconds() == 0l || con.getTelephones().size() == 0) {
            new Alert().getAlert(home.getActivity(), "Informativo", "Termine sua cofiguração").show();
        } else {
            if (home.isCronActive()) {
                home.setCronActive(false);
                home.getCron().cancel();
                ((ImageView) v.findViewById(R.id.safe)).setImageResource(R.drawable.sign_out);
            } else {
                home.updateCron();
                home.setCronActive(true);
                home.getCron().start();
                ((ImageView) v.findViewById(R.id.safe)).setImageResource(R.drawable.sign_in);
            }
            click = !click;
        }
    }

}
