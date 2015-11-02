/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.safecall.control.Alert;
import br.com.safecall.control.Controller;
import br.com.safecall.control.to.ReportItem;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Report {

    private Activity activity;
    private ListView lv;
    private ArrayList<ReportItem> reports;
    private Controller controller;

    public Report(final Activity activity, Controller controller) {
        this.activity = activity;
        lv = (ListView) activity.findViewById(R.id.reportList);
        this.controller = controller;
    }

    public void updateReport() {
        reports = controller.getReports();
        String configArray[] = new String[reports.size()];
        int i = 0;
        for (ReportItem item : reports) {
            configArray[i++] = item.getDate() + " - " + item.getTelephone();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, configArray);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message;
                message = "Data: " + reports.get(position).getDate() + "\n\n";
                message += "Status: " + reports.get(position).getStatus() + "\n\n";
                message += "Mensagem: " + reports.get(position).getMessage() + "\n";
                new Alert().getAlert(activity, reports.get(position).getTelephone(), message).show();
            }
        });
        lv.setOnItemLongClickListener(null);
    }

}
