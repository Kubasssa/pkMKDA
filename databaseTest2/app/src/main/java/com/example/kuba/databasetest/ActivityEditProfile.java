package com.example.kuba.databasetest;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuba.databasetest.objects.DatabaseHelper;

public class ActivityEditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Float age;
    Float weight;
    Float height;
    Double diff ,activity ,kcal;
    int kcalInt;
    String sex,login,password,temp;
    TextView diffInput;
    Double diffInputValue = 0.0;
    boolean ifUserExist, isInserted = true,isInserted2 =true;

    EditText ageInput, weightInput, heightInput,nameInput, passwordInput;
    Button addButton,button,button2, BackButton;

    DatabaseHelper helper;

    //TODO: updating all nutritional values, not only calories
    //TODO: add feature: change selected instead of all (e.f. only weight, only height ect)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);

        helper = new DatabaseHelper(getApplicationContext());

        final Spinner spinner = findViewById(R.id.editProfile_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.aktywność, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        weightInput = (EditText) findViewById(R.id.editProfile_edittext_weight_input);
        heightInput = (EditText) findViewById(R.id.editProfile_edittext_height_input);
        addButton = (Button) findViewById(R.id.editProfile_button_add);
        button = (Button) findViewById(R.id.editProfile_button_decrease);
        button2 = (Button) findViewById(R.id.editProfile_button_increase);
        diffInput = (TextView) findViewById(R.id.editProfile_textview_monthly_change);
        BackButton = (Button) findViewById(R.id.editProfile_button_back);

        //init value of diffInput
        diffInput.setText(String.format("%.1f",diffInputValue));

        //- button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diffInputValue -= 0.1;

                diffInput.setText(String.format("%.1f",diffInputValue));

                Vibrator vb = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(3);
            }
        });

        //+button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                diffInputValue += 0.1;
                diffInput.setText(String.format("%.1f",diffInputValue));

                Vibrator vb = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(3);

            }
        });


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = spinner.getSelectedItem().toString();
                diff=diffInputValue;

                if(TextUtils.isEmpty(weightInput.getText().toString())){
                    isInserted = false;
                }else {
                    weight = Float.valueOf(weightInput.getText().toString());
                    isInserted = true;
                }

                if(TextUtils.isEmpty(heightInput.getText().toString())){
                    isInserted2 = false;

                }else {
                    height = Float.valueOf(heightInput.getText().toString());
                    isInserted2 = true;
                }

                if(isInserted && isInserted2) {

                    Cursor allData = helper.getProfileSettings();

                    while (allData.moveToNext()) {
                        System.out.println(allData.getString(1));

                        sex = allData.getString(1);
                        height = allData.getFloat(2);
                        age = allData.getFloat(3);
                    }

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

                            helper.updateTotalCalories(kcalInt);
                            showToast("Edited successfully");

                    } else if (sex.equals("Male")) {
                        kcal = (((66 + (13.7 * weight) + (5 * height) - (6.76 * age))) * activity) - (233.3 * diff);
                        kcalInt = kcal.intValue();
                        showToast("The caloric demand is: " + kcalInt);

                            helper.updateTotalCalories(kcalInt);
                            showToast("Edited successfully");
                    }
                }else{
                    showToast("Enter all datas");
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
        Toast.makeText(ActivityEditProfile.this,text,Toast.LENGTH_SHORT).show();
    }
}

