package com.example.ecommerce;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName;
    private Button Addnewproductbtn;
    private EditText prod_name,prod_description,prod_price;
    private ImageView product_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        Addnewproductbtn=findViewById(R.id.add_new_product);
        prod_name=findViewById(R.id.product_name);
        prod_description=findViewById(R.id.product_description);
        prod_price=findViewById(R.id.product_price);
        product_image=findViewById(R.id.select_product_image);
        CategoryName=getIntent().getStringExtra("category").toString();
    }
}