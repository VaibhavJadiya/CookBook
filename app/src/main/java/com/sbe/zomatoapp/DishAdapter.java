package com.sbe.zomatoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MockView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.snapshot.IndexedNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class DishAdapter extends BaseAdapter {
    public  Context Mcontext;
    LayoutInflater inflater;
    ArrayList<DishHelper> arraylist;
    List<DishHelper> modelist;

    public DishAdapter(Context context, List<DishHelper> modelist) {
        Mcontext = context;
        this.arraylist = new ArrayList<DishHelper>();
        this.modelist = modelist;
        inflater=LayoutInflater.from(context);
        arraylist.addAll(modelist);
    }
    public class ViewHolder{
        TextView dish_name;
    }

    @Override
    public int getCount() {
        return modelist.size();
    }

    @Override
    public Object getItem(int i) {
        return modelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.reciper_listitem,null);
            holder.dish_name=view.findViewById(R.id.textView3);
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder) view.getTag();
        }
        holder.dish_name.setText(modelist.get(position).getName());
        return view;
    }
    public void filer(String charText){
        charText=charText.toLowerCase(Locale.getDefault());
        //modelist.clear();
        if(charText.length()==0){
            modelist.addAll(arraylist);
        }
        else {
            for (DishHelper helper:arraylist){
                if (helper.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    modelist.add(helper);
                    notifyDataSetChanged();
                }
            }
        }
        notifyDataSetChanged();
    }
}
