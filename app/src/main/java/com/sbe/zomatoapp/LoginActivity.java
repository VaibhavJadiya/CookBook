package com.sbe.zomatoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    Button btn_skipsignin,btn_signup,but_login;
    String str_number,str_password;
    TextInputEditText edit_number,edit_password;
    ProgressBar login_bar;
    TextInputLayout lay_number,lay_password;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Number = "number";
    public static final String Password = "password";
    public static final String Status = "status";
    int conf;

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_skipsignin=findViewById(R.id.skipsignin);
        btn_signup=findViewById(R.id.btn_signup);
        edit_number=findViewById(R.id.UernameText);
        edit_password=findViewById(R.id.textInputEditText2);
        but_login=findViewById(R.id.btn_login);
        login_bar=findViewById(R.id.login_progress);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_bar.setVisibility(View.VISIBLE);
                str_number=edit_number.getText().toString().trim();
                str_password=edit_password.getText().toString().trim();
                    Dataretrivre();


            }
        });
        //Declaring Shared Preffrences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
        btn_skipsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Status, "disable");
                editor.commit();
                startActivity(new Intent(LoginActivity.this,CuisineActivity.class));
            }
        });
    }
    public void Dataretrivre(){

        Query checkUser= FirebaseDatabase.getInstance().getReference("Users").orderByChild("number").equalTo(str_number);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    //retriving password
                    String system_password=snapshot.child(str_number).child("password").getValue(String.class);
                    String system_number=snapshot.child(str_number).child("number").getValue(String.class);
                    if (system_password.equals(str_password)&&system_number.equals(str_number)){
                        //Toast.makeText(SignInActivity.this, "Succefully Login", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        login_bar.setVisibility(View.GONE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(Number, str_number);
                        editor.putString(Password, str_password);
                        editor.putString(Status, "enable");
                        editor.commit();
                        Intent intent=new Intent(getApplicationContext(),CuisineActivity.class);
                        intent.putExtra("number",str_number);
                        startActivity(intent);
                                            }
                    else {
                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();


                    }

                }
                else{
                    Toast.makeText(LoginActivity.this, "User Dose'nt exists", Toast.LENGTH_SHORT).show();

                    //  l_progressbar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();
                // StyleableToast.makeText(SignInActivity.this,"Invalid Username Or Password",R.style.error).show();
                // l_progressbar.setVisibility(View.INVISIBLE);
            }
        });

    }
}