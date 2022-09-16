package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirt,sporttshirt,female_dress,sweaters,glasses,bags,hats,shoes,headphones,laptop,watch,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        tshirt=findViewById(R.id.t_shirts);
        sporttshirt=findViewById(R.id.sport_t_shirt);
        female_dress=findViewById(R.id.female_dresses);
        sweaters=findViewById(R.id.sweaters);
        glasses=findViewById(R.id.glasses);
        bags=findViewById(R.id.purses);
        hats=findViewById(R.id.hats_caps);
        shoes=findViewById(R.id.shoes);
        headphones=findViewById(R.id.headphones);
        laptop=findViewById(R.id.laptop);
        watch=findViewById(R.id.watches);
        mobile=findViewById(R.id.mobiles);

        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","tShirt");
                startActivity(intent);
            }
        });
        sporttshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sports tShirts");
                startActivity(intent);
            }
        });
        female_dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);
            }
        });
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sweaters");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);
            }
        });
        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Wallets Bags Purses");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","HeadPhones HandFree");
                startActivity(intent);
            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Laptop");
                startActivity(intent);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Watch");
                startActivity(intent);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Mobile Phones");
                startActivity(intent);
            }
        });
    }
}