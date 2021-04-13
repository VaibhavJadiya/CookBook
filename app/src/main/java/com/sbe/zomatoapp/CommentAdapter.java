package com.sbe.zomatoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class CommentAdapter extends ArrayAdapter<CommentHelperClass> {
    public Context context;
    public ArrayList<CommentHelperClass> comArray;


    public CommentAdapter(@NonNull Context context, ArrayList<CommentHelperClass> commArray) {
        super(context,0,commArray);
        this.context = context;
        this.comArray=commArray;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.comment_layout, null);

        }

        TextView username_cbs = convertView.findViewById(R.id.username_txt);
        TextView comment_cbs = convertView.findViewById(R.id.comment_txt);
        username_cbs.setText(getItem(position).getUser());
        comment_cbs.setText(getItem(position).getComment());
        Log.d("test",getItem(position).getUser()+getItem(position).getComment());

        return super.getView(position, convertView, parent);
    }
}
