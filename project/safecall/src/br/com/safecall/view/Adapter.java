/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.safecall.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<ListItem> myItems = new ArrayList<ListItem>();

    public void add() {
        if (!hasEmptyItems()) {
            ListItem listItem = new ListItem();
            listItem.caption = "";
            myItems.add(listItem);
            notifyDataSetChanged();
        }
    }

    public Adapter(Activity activity, ArrayList<String> items) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (items.size() > 0) {
            for (String item : items) {
                ListItem listItem = new ListItem();
                listItem.caption = item;
                myItems.add(listItem);
            }
        } else {
            ListItem listItem = new ListItem();
            listItem.caption = "";
            myItems.add(listItem);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return myItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.simple_edit_text, null);
            holder.caption = (EditText) convertView
                    .findViewById(R.id.item_caption);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        holder.caption.setText(myItems.get(position).caption);
        holder.caption.setId(position);
        //we need to update adapter once we finish with editing
        holder.caption.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                //if (!hasFocus) {
                final int position = v.getId();
                final EditText caption = (EditText) v;
                myItems.get(position).caption = caption.getText().toString();
                //}
            }
        });

        return convertView;
    }

    private boolean hasEmptyItems() {
        for (ListItem myItem : myItems) {
            if (myItem.caption.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}

class ViewHolder {

    EditText caption;
}

class ListItem {

    String caption;
}
