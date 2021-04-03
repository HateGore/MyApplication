package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String id="";
    public static String url = " https://5f8c88d3dbd6.ngrok.io";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        id = pref.getString("id","");
        if(id==null){
            id="";
        }
    }

    public void login(View view){
        if(!id.equals("")){
            Toast.makeText(this, "이미 로그인 했음", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void go_shop(View view){
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        id="";
        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.apply(); //비동기 처리
    }
}
