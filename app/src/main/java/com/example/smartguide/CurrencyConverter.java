package com.example.smartguide;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartguide.R;
import com.example.smartguide.databinding.ActivityCheckweatherBinding;
import com.example.smartguide.databinding.ActivityCurrencyconverterBinding;

public class CurrencyConverter extends AppCompatActivity {

    Spinner sp1,sp2;
    EditText ed1;
    Button b1;


    ActivityCurrencyconverterBinding bindin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindin = ActivityCurrencyconverterBinding.inflate(getLayoutInflater());
        setContentView(bindin.getRoot());

        sp1 = findViewById(R.id.spfrom);
        sp2 = findViewById(R.id.spto);
        ed1 = findViewById(R.id.txtamt);


        String[] from = {"USD","INR","EUR","AUD","CAD"};
        ArrayAdapter ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,from);
        sp1.setAdapter(ad);


        String[] to = {"Pakistani Rupees"};
        ArrayAdapter ad1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,to);
        sp2.setAdapter(ad1);
        b1 = findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double tot;
                Double amt = Double.parseDouble(ed1.getText().toString());
                if(sp1.getSelectedItem().toString() == "USD" && sp2.getSelectedItem().toString() == "Pakistani Rupees")
                {
                    tot = amt * 214.73;
                    bindin.textViewResult.setText(sp2.getSelectedItem().toString() + " : " +tot.toString());
                }
                else if(sp1.getSelectedItem().toString() == "INR" && sp2.getSelectedItem().toString() == "Pakistani Rupees")
                {
                    tot = amt * 2.70;
                    bindin.textViewResult.setText(sp2.getSelectedItem().toString() + " : " +tot.toString());
                }
                else if(sp1.getSelectedItem().toString() == "EUR" && sp2.getSelectedItem().toString() == "Pakistani Rupees")
                {
                    tot = amt * 218.57;
                    bindin.textViewResult.setText(sp2.getSelectedItem().toString() + " : " +tot.toString());
                }
                else if(sp1.getSelectedItem().toString() == "AUD" && sp2.getSelectedItem().toString() == "Pakistani Rupees")
                {
                    tot = amt * 149.52;

                    bindin.textViewResult.setText(sp2.getSelectedItem().toString() + " : " +tot.toString());
                }
                else if(sp1.getSelectedItem().toString() == "CAD" && sp2.getSelectedItem().toString() == "Pakistani Rupees")
                {
                    tot = amt * 166.62;
                    bindin.textViewResult.setText(sp2.getSelectedItem().toString() + " : " +tot.toString());
                }
            }
        });
    }
}