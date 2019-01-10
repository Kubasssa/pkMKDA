package com.example.magda.appx;

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
    Double diff;
    Double activity, kcal;
    String sex,name,surname,temp;
    TextView diffInput;
    Double diffInputValue = 0.0;

    EditText ageInput, weightInput, heightInput,nameInput, surnameInput;
    Button addButton,button,button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.Regist_Final);

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
        surnameInput = (EditText) findViewById(R.id.surnameInput);


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
                sex = spinner2.getSelectedItem().toString();
                temp = spinner.getSelectedItem().toString();
                name= nameInput.toString();
                surname= surnameInput.toString();
                diff=diffInputValue;

                if(temp.equals("Nikła-siedzący tryb życia")){activity=1.2; }
                else if(temp.equals("Mała-1-2 razy w tygodniu trening")){activity=1.35;}
                else if(temp.equals("Średnia-3-4 razy w tygodniu trening")){activity=1.55;}
                else if(temp.equals("Duża-3-4 razy w tygodniu trening/ praca fizyczna")){activity=1.75;}
                else if(temp.equals("Bardzo duża-zawodowni sportowcy/ trening codziennie")){activity=2.1;}


                if(sex.equals("Kobieta")){
                    kcal=(((665+(9.6*weight)+(1.8*height)-(4.7*age)))*activity)-(233.3*diff);
                    showToast("Zapotrzebowanie kaloryczne wynosi: "+kcal);
                }
                else if(sex.equals("Mężczyzna")){
                    kcal=(((66+(13.7*weight)+(5*height)-(6.76*age)))*activity)-(233.3*diff);
                    showToast("Zapotrzebowanie kaloryczne wynosi: "+kcal);
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
