package com.example.smartguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.smartguide.databinding.ActivityProfileBinding;

import java.util.Locale;

public class Profile extends AppCompatActivity {

    public static final String[] languages = {"Select Language", "English", "Urdu"};



    ActivityProfileBinding binding;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());






        // code for changing language
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setSelection(0);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedLang = adapterView.getItemAtPosition(i).toString();
                if (selectedLang.matches("English")){
                    setlocale(Profile.this , "en");
                    finish();
                    startActivity(getIntent());
                }else if (selectedLang.matches("Urdu")){
                    setlocale(Profile.this , "ur");
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });









        binding.logout.setOnClickListener(v->{
            sp = getSharedPreferences("userData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("logedin" , 0);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(),login_register.class);
            startActivity(intent);
        });

        binding.llExplore.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), CityMenu.class);
            startActivity(intent);
        });

    }
    public void setlocale(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}