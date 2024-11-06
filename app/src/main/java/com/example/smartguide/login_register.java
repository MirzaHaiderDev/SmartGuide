package com.example.smartguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smartguide.databinding.ActivityLoginRegisterBinding;

public class login_register extends AppCompatActivity {

    private ActivityLoginRegisterBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.register.setOnClickListener(v->{
            String userName = binding.editTextUserName.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            databaseHelper.registerUser(new Data(userName , password));
        });

        binding.login.setOnClickListener(v->{
            String userName = binding.editTextUserName.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            Boolean check = databaseHelper.loginUser(new Data(userName , password));
            if (check == true){
                Toast.makeText(this, "login sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login_register.this , CityMenu.class);
                startActivity(intent);
                finish();
            }
            else if (check != true){
                Toast.makeText(this, "UserName or Password is incorrect", Toast.LENGTH_SHORT).show();
            }
        });

    }
}