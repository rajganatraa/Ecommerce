package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button loginbtn,registerbtn;
    String parentdbname="Users";
    private ProgressDialog loadingbar;

    DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        loginbtn=findViewById(R.id.main_login_btn);
        registerbtn=findViewById(R.id.main_join_now_btn);
        loadingbar=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        String UserPhonekey=Paper.book().read(Prevalent.UPHONENUMBER);
        String Userpasswordkey=Paper.book().read(Prevalent.UPASSWORD);

        if(UserPhonekey !="" && Userpasswordkey !=""){
            if(!TextUtils.isEmpty(UserPhonekey) && !TextUtils.isEmpty(Userpasswordkey)){
                AllowAccess(UserPhonekey,Userpasswordkey);
                loadingbar.setTitle("Already Logged in");
                loadingbar.setMessage("Please Wait! We are redirecting you ...."+" "+UserPhonekey+" "+Userpasswordkey);
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }
    }

    private void AllowAccess(String uphnumber, String upassword) {

        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(MainActivity.this, "data:"+uphnumber+" "+upassword, Toast.LENGTH_SHORT).show();
                if(snapshot.child("Users").child(uphnumber).exists()){
                    Toast.makeText(MainActivity.this, "Data exists..", Toast.LENGTH_SHORT).show();
                    Users userdata=snapshot.child(parentdbname).child(uphnumber).getValue(Users.class);
                    if(userdata.getPhone().equals(uphnumber) && userdata.getPassword().equals(upassword)){
                        Toast.makeText(MainActivity.this, "Login Successfull...", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                        Intent intent=new Intent(MainActivity.this,
                                HomeActivity.class);
                        startActivity(intent );
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Please enter correct credentials", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Sorry! Account not found....", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(MainActivity.this, "You need to create a new account...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}