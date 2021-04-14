package com.sbe.zomatoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    TextView dish_name,recipe_txt;
    ListView comment_list;
    Button cmt_sned;
    ImageView favort_btn;
    EditText cmt_edit;
    String comment_content;
    String remsimpi;
    ArrayList<String> reciepelist=new ArrayList<String>();
    SqlHelper sqlHelperClass;
    DatabaseReference mRef,mRef2;
    String db_dish,db_cusisine;
    int i=1;
    CommentAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Number = "number";
    public static final String Status = "status";
    //Firebase ListView
    FirebaseListAdapter fAdapter;
    ArrayList<String> UserArray=new ArrayList<>();
    ArrayList<String> CommArray=new ArrayList<>();
    String db_number,db_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        dish_name=findViewById(R.id.dish_title);
        recipe_txt=findViewById(R.id.recipe_text);
        cmt_sned=findViewById(R.id.comt_send_btn);
        cmt_edit=findViewById(R.id.comment_edit);
        comment_list=findViewById(R.id.comment_list);
        favort_btn=findViewById(R.id.rec_favourate);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db_status= sharedPreferences.getString(Status,"NA");
        db_number= sharedPreferences.getString(Number,"NA");

        favort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db_status.equals("disable")){

                    Toast.makeText(RecipeActivity.this, "Only For Registered Users", Toast.LENGTH_SHORT).show();
                }
                else {
                    i++;
                    ButtonChanger(i);
                }

            }
        });
        Intent intent=getIntent();
        db_dish=intent.getStringExtra("dish");
       db_cusisine=intent.getStringExtra("cusine");
        datasiaplayer();
        cmt_sned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db_status.equals("disable")){
                    Toast.makeText(RecipeActivity.this, "Only For Registered Users", Toast.LENGTH_SHORT).show();
                }
                else {
                    comment_content = cmt_edit.getText().toString().trim();
                    mRef2 = FirebaseDatabase.getInstance().getReference().child("Cuisiness").child(db_cusisine).child(db_dish).child("comments");
                    CommentHelperClass commentHelperClass = new CommentHelperClass(db_number, comment_content);
                    mRef2.child(db_number).setValue(commentHelperClass);
                }
            }
        });

        dish_name.setText(db_dish);
        Query query= FirebaseDatabase.getInstance().getReference().child("Cuisiness").child(db_cusisine).child(db_dish).child("comments");
        FirebaseListOptions<CommentHelperClass>options=new FirebaseListOptions.Builder<CommentHelperClass>()
                .setLayout(R.layout.comment_layout)
                .setQuery(query,CommentHelperClass.class)
                .build();
        fAdapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView user_txt = v.findViewById(R.id.username_txt);
                TextView comm_txt = v.findViewById(R.id.comment_txt);
                CommentHelperClass DateObj = (CommentHelperClass) model;
                UserArray.add(String.valueOf(DateObj.getUser()));
                CommArray.add(String.valueOf(DateObj.getComment()));
                user_txt.setText(DateObj.getUser().toString());
                comm_txt.setText(DateObj.getComment().toString());

            }
        };
        comment_list.setAdapter(fAdapter);

        mRef= FirebaseDatabase.getInstance().getReference().child("Cuisiness").child(db_cusisine).child(db_dish);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // String user=snapshot.child("user").getValue(String.class);
                //String comm=snapshot.child("comment").getValue(String.class);
                String remsimpi=snapshot.child("cont").getValue(String.class);
                recipe_txt.setText(remsimpi);
              //  Toast.makeText(RecipeActivity.this, remsimpi, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ButtonChanger(int i) {
        if (i%2==0){
            favort_btn.setBackgroundResource(R.drawable.ic_favourate_selected);
            sqlHelperClass=new SqlHelper(RecipeActivity.this);
            sqlHelperClass.putInfo(db_dish,db_cusisine);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Fav",i);
            editor.commit();
            Toast.makeText(RecipeActivity.this, "Added To Favourite", Toast.LENGTH_SHORT).show();

        }
        else {
            favort_btn.setBackgroundResource(R.drawable.ic_favourate_unselected);
            sqlHelperClass=new SqlHelper(RecipeActivity.this);

            sqlHelperClass.DeleteUser(sqlHelperClass,db_dish,db_cusisine);
           // Toast.makeText(RecipeActivity.this, db_dish+"Deleted", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Removed from Favourite", Toast.LENGTH_SHORT).show();
        }
    }
    void datasiaplayer(){
        sqlHelperClass=new SqlHelper(RecipeActivity.this);
        Cursor cursor=sqlHelperClass.getInfo();
      //  Toast.makeText(this, "DataDisplayer Loaded", Toast.LENGTH_SHORT).show();
        if (cursor.getCount()==0){
            //Toast.makeText(RecipeActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
          //  Toast.makeText(this, "Cursor Not Found", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                reciepelist.clear();
                reciepelist.add(cursor.getString(1));
              //  Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();
               // Toast.makeText(this, db_dish, Toast.LENGTH_SHORT).show();
                if (reciepelist.contains(db_dish)){
                    favort_btn.setBackgroundResource(R.drawable.ic_favourate_selected);
                   // Toast.makeText(this, "Fecthed", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        fAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fAdapter.stopListening();
    }

}