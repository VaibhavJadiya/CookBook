package com.sbe.zomatoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

public class SignupActivity extends AppCompatActivity {

    String str_anme,str_email,str_number,str_password;
    TextInputEditText edit_name,edit_email,edit_number,edit_password;
    TextInputLayout lay_name,lay_email,lay_number,lay_password;
    Button btn_createaccnt;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Data" ;
    public static final String Number = "sp_number";
    public static final String Password = "sp_password";
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edit_name=findViewById(R.id.UernameText4);
        edit_email=findViewById(R.id.UernameText);
        edit_number=findViewById(R.id.textInputEditText5);
        edit_password=findViewById(R.id.textInputEditText2);
        btn_createaccnt=findViewById(R.id.btn_createaccount);
        bar=findViewById(R.id.signup_progress);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        btn_createaccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootnode=FirebaseDatabase.getInstance();
                reference=rootnode.getReference("Users");

                str_anme=edit_name.getText().toString().trim();


                str_email=edit_email.getText().toString().trim();
                str_number=edit_number.getText().toString().trim();
                str_password=edit_password.getText().toString().trim();

                bar.setVisibility(View.VISIBLE);
                UserHelperClass helperClass=new UserHelperClass(str_anme,str_email,str_number,str_password);

                reference.child(str_number).setValue(helperClass);
                bar.setVisibility(View.INVISIBLE);
                 Toast.makeText(SignupActivity.this, "Registered Sucessful!", Toast.LENGTH_SHORT).show();
                //StyleableToast.makeText(SignupActivity.this,"Registration Succefull",R.style.success).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Number, str_number);
                editor.putString(Password, str_password);
                editor.commit();
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));

            }
        });

    }
}