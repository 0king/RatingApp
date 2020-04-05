package com.example.ratingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ratingapp.R;
import com.example.ratingapp.ui.navigation.Router;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Router.INSTANCE.init(getSupportFragmentManager());
        if (savedInstanceState==null){
            Router.INSTANCE.toHomeScreen();
        }
    }
}
