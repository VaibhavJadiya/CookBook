package com.sbe.zomatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CuisineActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_greece,img_chinese,img_japanese,img_italian,img_philippines,img_indian;
    String cuisine;
    Intent intent;
    ImageView fav_img;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Number = "number";
    public static final String Status = "status";
    String db_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
        img_greece=findViewById(R.id.item_greece);
        img_chinese=findViewById(R.id.item_chinese);
        img_japanese=findViewById(R.id.item_japanese);
        img_italian=findViewById(R.id.item_italian);
        fav_img=findViewById(R.id.favorate_heart_btn);
        img_philippines=findViewById(R.id.item_filipino);
        img_indian=findViewById(R.id.item_indian);
        img_greece.setOnClickListener(this);
        img_chinese.setOnClickListener(this);
        img_japanese.setOnClickListener(this);
        img_italian.setOnClickListener(this);
        img_philippines.setOnClickListener(this);
        img_indian.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db_status= sharedPreferences.getString(Status,"NA");

        fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db_status.equals("disable")){
                    Toast.makeText(CuisineActivity.this, "Only For Registered Users", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(CuisineActivity.this, FavouraiteActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.item_greece:
                intent=new Intent(CuisineActivity.this,DishActivity.class);
                intent.putExtra("cuisine","greece");
                startActivity(intent);
                break;
            case R.id.item_chinese:
                intent=new Intent(CuisineActivity.this,DishActivity.class);
                intent.putExtra("cuisine","chinese");
                startActivity(intent);
                break;
            case R.id.item_japanese:
                intent=new Intent(CuisineActivity.this,DishActivity.class);
                intent.putExtra("cuisine","japanese");
                startActivity(intent);
                break;
            case R.id.item_italian:
                intent=new Intent(CuisineActivity.this,DishActivity.class);
                intent.putExtra("cuisine","italian");
                startActivity(intent);
                break;
            case R.id.item_filipino:
                intent=new Intent(CuisineActivity.this,DishActivity.class);
                intent.putExtra("cuisine","filipino");
                startActivity(intent);
                break;
            case R.id.item_indian:
                intent=new Intent(CuisineActivity.this,DishActivity.class);
                intent.putExtra("cuisine","Indian");
                startActivity(intent);
                break;

        }
    }
}