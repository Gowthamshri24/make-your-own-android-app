package com.android.gowtham.city_hunt;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SpecialAdapter1 extends SimpleAdapter {

    String cate;
    List<HashMap<String, String>> mylist;
    private String[] aray;
    TextView title;

    public SpecialAdapter1(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        mylist = items;
        aray = from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
            cate = mylist.get(position).get(aray[3]);
        switch (cate) {
            case "Festival":
                title=(TextView)view.findViewById(R.id.textView33);
                title.setTextColor(Color.parseColor("#00c3ff"));
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.festival,0,0,0);
                break;
            case "Sports":
                title=(TextView)view.findViewById(R.id.textView33);
                title.setTextColor(Color.parseColor("#FF7F4FF1"));
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sports,0,0,0);
                break;
            case "Business":
                title=(TextView)view.findViewById(R.id.textView33);
                title.setTextColor(Color.parseColor("#FF52CCC8"));
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.busnss,0,0,0);
                break;
            case "College":
                title=(TextView)view.findViewById(R.id.textView33);
                title.setTextColor(Color.parseColor("#fea500"));
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.college,0,0,0);
                break;
            case "Music":
                title=(TextView)view.findViewById(R.id.textView33);
                title.setTextColor(Color.parseColor("#fff25667"));
                title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.events,0,0,0);
                break;
        }
        return view;
    }
}