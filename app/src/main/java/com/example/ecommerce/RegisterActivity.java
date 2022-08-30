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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button registerbtn;
    private EditText name,ph_number,password,cnf_password;
    private ProgressDialog loadingbar;
    boolean passwordVisible;
    DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerbtn=findViewById(R.id.register_btn);
        name=findViewById(R.id.register_name);
        ph_number=findViewById(R.id.register__phone_number);
        password=findViewById(R.id.register_password);
        cnf_password=findViewById(R.id.register_cnf_password);
        loadingbar=new ProgressDialog(this);
//        rootref= FirebaseDatabase.getInstance().getReference();


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

        cnf_password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final  int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=cnf_password.getRight()-cnf_password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=cnf_password.getSelectionEnd();
                        if(passwordVisible)
                        {
                            cnf_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.password_not_visible,0);
                            //for hide password
                            cnf_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            cnf_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.password_visible,0);
                            //for hide password
                            cnf_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        cnf_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void CreateAccount() {
        String uname=name.getText().toString();
        String uphnumber=ph_number.getText().toString();
        String upassword=password.getText().toString();
        String ucnfpassword=cnf_password.getText().toString();
//        rootref= FirebaseDatabase.getInstance().getReference();

        if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(uphnumber) && !TextUtils.isEmpty(upassword) && !TextUtils.isEmpty(ucnfpassword)){
            loadingbar.setTitle("Create Account");
            loadingbar.setMessage("Please Wait! We are checking your credentials and creating account....");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            ValidatePhonenumber(uname,uphnumber,upassword,ucnfpassword);
        }
        else{
            Toast.makeText(this, "Please enter all the details......", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidatePhonenumber(String uname, String uphnumber, String upassword,String cnfpass) {

//        rootref= FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(uphnumber).exists())){
                    if(upassword.equals(cnfpass)){
                        HashMap<String,Object> userdata=new HashMap<>();
                        userdata.put("phone",uphnumber);
                        userdata.put("name",uname);
                        userdata.put("password",upassword);

                        rootref.child("Users").child(uphnumber).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Congratulations! Your account is created successfully..", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent );
                                }
                                else{
                                    loadingbar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Something went wrong! Please try again..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Please enter the same password", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this, uphnumber+" already exists...", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again with another phone number...", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}