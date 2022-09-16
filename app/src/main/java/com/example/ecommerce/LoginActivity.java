package com.example.ecommerce;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText phnumber,password;
    private Button loginbtn;
    private ProgressDialog loadingbar;
    private TextView adminlink,notadminlink;
    String uphnumber,upassword;
    private String parentdbname="Users";
    boolean passwordVisible;
    private CheckBox chboxrememberme;
    DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn=findViewById(R.id.login_login_btn);
        phnumber=findViewById(R.id.Login_phone_number);
        password=findViewById(R.id.Login_password);
        loadingbar=new ProgressDialog(this);
        adminlink=findViewById(R.id.admin_panel);
        notadminlink=findViewById(R.id.not_admin_panel);
        chboxrememberme=findViewById(R.id.remember_me_chb);
        Paper.init(this);

        uphnumber=phnumber.getText().toString();
        upassword=password.getText().toString();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser(uphnumber,upassword);
            }
        });
        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginbtn.setText("Login Admin");
                adminlink.setVisibility(View.INVISIBLE);
                notadminlink.setVisibility(View.VISIBLE);
                parentdbname="Admins";
            }
        });
        notadminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginbtn.setText("Login");
                adminlink.setVisibility(View.VISIBLE);
                notadminlink.setVisibility(View.INVISIBLE);
                parentdbname="Users";
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final  int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password.getSelectionEnd();
                        if(passwordVisible)
                        {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.password_not_visible,0);
                            //for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.password_visible,0);
                            //for hide password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void LoginUser(String uphnumber, String upassword) {

        uphnumber=phnumber.getText().toString();
        upassword=password.getText().toString();

        if(!TextUtils.isEmpty(uphnumber) && !TextUtils.isEmpty(upassword)){
            loadingbar.setTitle("Login Account");
            loadingbar.setMessage("Please Wait! We are checking your credentials ....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            AllowAcessToAccount(uphnumber,upassword);
        }
        else{
            Toast.makeText(this, "Please fill all the details...", Toast.LENGTH_SHORT).show();
        }
    }

    private void AllowAcessToAccount(String uphnumber, String upassword) {
        if(chboxrememberme.isChecked()){
            Paper.book().write(Prevalent.UPHONENUMBER,uphnumber);
            Paper.book().write(Prevalent.UPASSWORD,upassword);
        }
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(parentdbname).child(uphnumber).exists()){
                    Users userdata;
                    userdata = snapshot.child(parentdbname).child(uphnumber).getValue(Users.class);
                    if(userdata.getPhone().equals(uphnumber) && userdata.getPassword().equals(upassword)){

                        if(parentdbname.equals("Admins")){
                            Toast.makeText(LoginActivity.this, "Login Admin Successful...", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent=new Intent(LoginActivity.this,AdminCategoryActivity.class);
                            startActivity(intent );
                            finish();
                        }
                        else if(parentdbname.equals("Users")){
                            Toast.makeText(LoginActivity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent );
                            finish();
                        }

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Please enter correct credentials", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Sorry! Account not found....", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this, "You need to create a new account...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}