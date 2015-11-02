/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import br.com.safecall.control.Controller;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Config {

    private Activity activity;
    private Controller controller;

    public Config(Activity activity, Controller controller) {
        this.activity = activity;
        this.controller = controller;
    }

    public Dialog getCronDialog() {
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.number_picker);
        Long totalMinutes = controller.getConfig().getSeconds() / 60;
        Long hour = totalMinutes / 60;
        Long minutes = totalMinutes - (60 * hour);
        final NumberPicker npMin = (NumberPicker) dialog.findViewById(R.id.minutePicker);
        npMin.setWrapSelectorWheel(false);
        npMin.setMaxValue(59);
        npMin.setValue(minutes.intValue());
        final NumberPicker npHour = (NumberPicker) dialog.findViewById(R.id.hourPicker);
        npHour.setWrapSelectorWheel(false);
        npHour.setMaxValue(24);
        npHour.setValue(hour.intValue());
        ImageView save = (ImageView) dialog.findViewById(R.id.iv_save_cron);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long minutes = (long) (npMin.getValue() + (npHour.getValue() * 60));
                controller.setMinutes(minutes);
                dialog.dismiss();
            }
        });
        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_cancel_cron);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public Dialog getTelDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.list_view);
        final ListView list = (ListView) dialog.findViewById(R.id.list_view);
        list.setItemsCanFocus(true);
        final Adapter listAdapter = new Adapter(activity, controller.getConfig().getTelephones());
        list.setAdapter(listAdapter);
        ImageView add = (ImageView) dialog.findViewById(R.id.iv_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clearFocus();
                listAdapter.add();
            }
        });
        ImageView save = (ImageView) dialog.findViewById(R.id.iv_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listAdapter.notifyDataSetChanged();
                list.clearFocus();
                ArrayList<String> list = new ArrayList<String>();
                for (ListItem item : listAdapter.myItems) {
                    if (!item.caption.isEmpty()) {
                        list.add(item.caption);
                    }
                }
                controller.setTelephones(list);
            }
        });
        return dialog;
    }

    public Dialog getMessageDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.message_set);
        final EditText et = (EditText) dialog.findViewById(R.id.editTextMessage);
        et.setText(controller.getConfig().getMessage());
        ImageView save = (ImageView) dialog.findViewById(R.id.iv_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setMessage(et.getText().toString());
                dialog.dismiss();
            }
        });
        ImageView cancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
