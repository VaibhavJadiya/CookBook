package com.sbe.zomatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouraiteActivity extends AppCompatActivity {

    ListView favlist;
    TextView fav_txt_empt;
    FavorateAdapater arrayAdapter;
    SqlHelper sqlHelperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favouraite);
        ArrayList<DishHelper> NameArray=new ArrayList<DishHelper>();
        favlist=findViewById(R.id.favourate_list);
        fav_txt_empt=findViewById(R.id.empty_fav_txt);

            sqlHelperClass=new SqlHelper(FavouraiteActivity.this);
            Cursor cursor=sqlHelperClass.getInfo();
            if (cursor.getCount()==0){
                Toast.makeText(FavouraiteActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                fav_txt_empt.setVisibility(View.VISIBLE);
                favlist.setVisibility(View.GONE);
            }
            else {
                while (cursor.moveToNext()){

                    NameArray.add(new DishHelper(cursor.getString(1),cursor.getString(2)));
                }
            }
        arrayAdapter = new FavorateAdapater(this,NameArray);
        favlist.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


    }
}