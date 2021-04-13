package com.sbe.zomatoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import android.widget.SearchView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class DishActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;

    DatabaseReference mRef;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Status = "status";
    ProgressBar dish_bar;
    DishAdapter arrayAdapter;
    String db_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        dish_bar=findViewById(R.id.dish_progress);
        dish_bar.setVisibility(View.VISIBLE);
        toolbar=findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        ArrayList<DishHelper> arrayList=new ArrayList<DishHelper>();
        final String cuisnine=intent.getStringExtra("cuisine");


       // Toast.makeText(this, cuisnine, Toast.LENGTH_SHORT).show();
        toolbar.setTitle(cuisnine);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        db_status= sharedPreferences.getString(Status,"NA");


        listView=findViewById(R.id.dish_list);
       // searchView=findViewById(R.id.searchView);

      //  progressBar=findViewById(R.id.dish_progres);
        DishHelper helper=new DishHelper();
        //Getting Name and username

        //Here the game Begins
        mRef= FirebaseDatabase.getInstance().getReference().child("Cuisiness").child(cuisnine);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.child("name").getValue(String.class);

                arrayList.add(new DishHelper(value,cuisnine));
                arrayAdapter.notifyDataSetChanged();
                dish_bar.setVisibility(View.GONE);

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
        arrayAdapter = new DishAdapter(this,arrayList);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent1=new Intent(DishActivity.this,RecipeActivity.class);
                DishHelper modeler = arrayList.get(i);
                String word = modeler.getName();
                intent1.putExtra("dish",word);
                intent1.putExtra("cusine",cuisnine);
                startActivity(intent1);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem MyActionMenu=menu.findItem(R.id.seacrh_option);
        SearchView searchView=(SearchView) MyActionMenu.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // Toast.makeText(DishActivity.this, "Non Filetrsz", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    arrayAdapter.filer("");
                    listView.clearTextFilter();
                  //  Toast.makeText(DishActivity.this, "Fultyer", Toast.LENGTH_SHORT).show();
                }
                else {
                    arrayAdapter.filer(newText);
                  //  Toast.makeText(DishActivity.this, newText, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuid=item.getItemId();
        switch (menuid){
            case R.id.addrecipe_option:

                if (db_status.equals("disable")){
                    Toast.makeText(DishActivity.this, "Only For Registered Users", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(DishActivity.this, AddRecipeActivity.class));
                }
                break;
            case R.id.seacrh_option:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}