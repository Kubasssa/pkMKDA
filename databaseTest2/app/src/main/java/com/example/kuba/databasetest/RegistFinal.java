package com.example.kuba.databasetest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistFinal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Float age;
    Float weight;
    Float height;
    Double diff ,activity ,kcal;
    int kcalInt;
    String sex,login,password,temp;
    TextView diffInput;
    Double diffInputValue = 0.0;
    boolean ifUserExist;

    EditText ageInput, weightInput, heightInput,nameInput, passwordInput;
    Button addButton,button,button2;

    DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);

        helper = new DatabaseHelper(getApplicationContext());

        final Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.aktywność, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.płeć, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        ageInput = (EditText) findViewById(R.id.ageInput);

        weightInput = (EditText) findViewById(R.id.weightInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        addButton = (Button) findViewById(R.id.add);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        diffInput = (TextView) findViewById(R.id.diffInput);
        nameInput = (EditText) findViewById(R.id.nameInput);
        passwordInput = (EditText) findViewById(R.id.surnameInput);


        //init value of diffInput
        diffInput.setText(String.format("%.1f",diffInputValue));

        //- button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diffInputValue -= 0.1;

                diffInput.setText(String.format("%.1f",diffInputValue));
            }
        });

        //+button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                diffInputValue += 0.1;
                diffInput.setText(String.format("%.1f",diffInputValue));

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = Float.valueOf(ageInput.getText().toString());
                height = Float.valueOf(heightInput.getText().toString());
                weight = Float.valueOf(weightInput.getText().toString());
                sex = spinner2.getSelectedItem().toString().trim();
                temp = spinner.getSelectedItem().toString();
                login = nameInput.getText().toString().trim();
                password = passwordInput.getText().toString().trim();
                diff=diffInputValue;

                Cursor allData = helper.getAllData();

                while (allData.moveToNext()){
                    System.out.println(allData.getString(1));
                    System.out.println(login);
                    if(login.equals(allData.getString(1))){
                        Toast.makeText(getApplicationContext(),"This Login already exist",Toast.LENGTH_SHORT).show();
                        ifUserExist = true;

                    }else{
                        System.out.println("jeblo cos");
                        ifUserExist = false;
                    }
                }

                if(!ifUserExist) {

                    if (temp.equals("Very Low-sedentary lifestyle")) {
                        activity = 1.2;
                    } else if (temp.equals("Low-training 1-2 times a week")) {
                        activity = 1.35;
                    } else if (temp.equals("Average-training 3-4 times a week")) {
                        activity = 1.55;
                    } else if (temp.equals("Large-training 3-4 times a week / physical work")) {
                        activity = 1.75;
                    } else if (temp.equals("Very large-competitive athletes / training every day")) {
                        activity = 2.1;
                    }


                    if (sex.equals("Female")) {
                        kcal = (((665 + (9.6 * weight) + (1.8 * height) - (4.7 * age))) * activity) + (233.3 * diff);
                        kcalInt = kcal.intValue();
                        showToast("The caloric demand is: " + kcalInt);

                        helper.addTotalCaloriesToEat(kcalInt);
                        boolean isInserted = helper.insertUserData(login, password, sex);

                        if (isInserted == true) {
                            helper.insertProfileSettings(sex,Math.round(height),Math.round(age));
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            showToast("jeblo cos ");
                            //  Toast.makeText(applicationContext,"Data Could not be insereted",Toast.LENGTH_SHORT).show()
                        }
                    } else if (sex.equals("Male")) {
                        kcal = (((66 + (13.7 * weight) + (5 * height) - (6.76 * age))) * activity) - (233.3 * diff);
                        kcalInt = kcal.intValue();
                        showToast("The caloric demand is:  " + kcalInt);

                        helper.addTotalCaloriesToEat(kcalInt);
                        boolean isInserted = helper.insertUserData(login, password, sex);

                        if (isInserted == true) {
                            helper.insertProfileSettings(sex,Math.round(height),Math.round(age));
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            showToast("jeblo cos ");
                            //  Toast.makeText(applicationContext,"Data Could not be insereted",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void showToast(String text){
        Toast.makeText(RegistFinal.this,text,Toast.LENGTH_SHORT).show();
    }
}
