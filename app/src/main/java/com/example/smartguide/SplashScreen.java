package com.example.smartguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.smartguide.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    Handler h = new Handler();

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        h.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (checkUserLogedin()){
                    Toast.makeText(getApplicationContext(), "loged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, CityMenu.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Not loged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, login_register.class);
                    startActivity(intent);
                    finish();
                }



            }
        },3000);

    }

    private boolean checkUserLogedin(){
        boolean check;
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userData" , Context.MODE_PRIVATE);
        int logedin = sp.getInt("logedin",0);

        if (logedin == 1){
            check = true;
        }else{
            check = false;
        }
        return check;
    }
}