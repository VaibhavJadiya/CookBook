package com.sbe.zomatoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class FavorateAdapater extends ArrayAdapter<DishHelper> {

    public Context context;
    public ArrayList<DishHelper> list;

    public FavorateAdapater(@NonNull Context context, ArrayList<DishHelper> list) {
        super(context,0,list);
        this.context = context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.reciper_listitem, null);
        }
        TextView tittle=convertView.findViewById(R.id.textView3);
        TextView category=convertView.findViewById(R.id.category_txt);
        tittle.setText(getItem(position).getName());
        category.setText(getItem(position).getCategory());

        return convertView;
    }
}
