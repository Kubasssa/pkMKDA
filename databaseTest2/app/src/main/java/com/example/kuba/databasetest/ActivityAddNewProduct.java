package com.example.kuba.databasetest;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuba.databasetest.objects.DatabaseHelper;
import com.example.kuba.databasetest.objects.Item;

public class ActivityAddNewProduct extends AppCompatActivity {
    EditText etProductName;
    EditText etCalories;
    EditText etCarbs;
    EditText etFat;
    EditText etProteins;
    Button bReturn;
    Button bAdd;

    DatabaseHelper db;
    Item newProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_new_product);
        initVariables();

        bReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Vibrator vb = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(5);
                finish();
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if( String.valueOf(etProductName.getText()).equals("")  ||
                        String.valueOf(etCalories.getText()).equals("")   ||
                        String.valueOf(etCarbs.getText()).equals("")  ||
                        String.valueOf(etFat.getText()).equals("") ||
                        String.valueOf(etProteins.getText()).equals("")) {
                    showToast("Fill all values");
                }else {
                    newProduct = new Item(String.valueOf(
                            etProductName.getText()),
                            "100g",
                            Integer.parseInt(String.valueOf(etCalories.getText())),
                            Double.parseDouble(String.valueOf(etCarbs.getText())),
                            Double.parseDouble(String.valueOf(etFat.getText())),
                            Double.parseDouble(String.valueOf(etProteins.getText())));

                    db.addNewProduct(newProduct);
                    showToast("New product added!");
                    finish();
                }
            }
        });
    }

    public void initVariables()
    {
        db = new DatabaseHelper(getApplicationContext());
        etProductName = findViewById(R.id.addNewProduct_input_name);
        etCalories = findViewById(R.id.addNewProduct_input_calories);
        etCarbs = findViewById(R.id.addNewProduct_input_carbs);
        etFat = findViewById(R.id.addNewProduct_input_fat);
        etProteins = findViewById(R.id.addNewProduct_input_proteins);
        bReturn = findViewById(R.id.addNewProduct_button_return);
        bAdd = findViewById(R.id.addNewProduct_button_add_to_database);
    }

    private void showToast(String text){
        Toast.makeText(ActivityAddNewProduct.this, text,Toast.LENGTH_LONG).show();
    }
}
