/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.control;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import java.util.List;

/**
 *
 * @author User
 */
public class Location {

    private Activity activity;
    private Geocoder geocoder;
    private String bestProvider;
    private List<Address> user = null;
    double lat;
    double lng;

    public Location(Activity activity) {
        this.activity = activity;
    }

    public String getLocation() {
        String loc = null;
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        android.location.Location location = lm.getLastKnownLocation(bestProvider);

        if (location!= null) {
            geocoder = new Geocoder(activity);
            try {
                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                lat = (double) user.get(0).getLatitude();
                lng = (double) user.get(0).getLongitude();
                loc = "lat: " + lat + ",  longitude: " + lng;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loc;
    }
}
