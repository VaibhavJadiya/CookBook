package com.sbe.zomatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRecipeActivity extends AppCompatActivity {

    Spinner spinner;
    String cusine_category,rec_name,rec_cont;
    Button btn_add;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    int x;
    TextInputEditText recipe_name_txt,recipe_cont_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        spinner=findViewById(R.id.spinnerid);
        recipe_name_txt=findViewById(R.id.Rec_nameText);
        recipe_cont_txt=findViewById(R.id.rec_cont_Text);
        btn_add=findViewById(R.id.crate_recipe);
        String[] mTestArray = getResources().getStringArray(R.array.cusines_array);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(
                this,R.array.cusines_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cusine_category=mTestArray[i];
                    x=1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    x=0;
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cusine_category.equals("Select a Cuisine")||x==0){
                    Toast.makeText(AddRecipeActivity.this, "Please Select a Cuisine", Toast.LENGTH_SHORT).show();
                }
                else {
                    rec_name=recipe_name_txt.getText().toString().trim();
                    rec_cont=recipe_cont_txt.getText().toString().trim();
                    rootnode=FirebaseDatabase.getInstance();
                    reference=rootnode.getReference("Cuisiness").child(cusine_category);
                    CreateDishHelper helperClass=new CreateDishHelper(rec_name,rec_cont,cusine_category);
                    reference.child(rec_name).setValue(helperClass);
                    reference.child(rec_name).child("recipe").setValue(helperClass);
                    Intent intent=new Intent(AddRecipeActivity.this,CuisineActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}