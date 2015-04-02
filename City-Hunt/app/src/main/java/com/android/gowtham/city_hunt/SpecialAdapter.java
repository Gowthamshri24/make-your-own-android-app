package com.android.gowtham.city_hunt;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;

public class SpecialAdapter extends SimpleAdapter {

    String cate;
    List<HashMap<String, String>> mylist;
    private String[] aray;

    public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        mylist = items;
        aray = from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
            cate = mylist.get(position).get(aray[4]);
        switch (cate) {
            case "Festival":
                view.setBackgroundColor(Color.parseColor("#00c3ff"));
                break;
            case "Sports":
                view.setBackgroundColor(Color.parseColor("#FF7F4FF1"));
                break;
            case "Business":
                view.setBackgroundColor(Color.parseColor("#FF52CCC8"));
                break;
            case "College":
                view.setBackgroundColor(Color.parseColor("#fea500"));
                break;
            case "Events":
                view.setBackgroundColor(Color.parseColor("#FFF25667"));
                break;
        }
        return view;
    }
}