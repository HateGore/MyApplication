package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText id_txt;
    private EditText pwd_txt;
    private RequestHttpURLConnection http;
    public boolean flag;
    private String id;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_txt = findViewById(R.id.editText);
        pwd_txt = findViewById(R.id.editText2);

        http = new RequestHttpURLConnection();
    }
    public void go_login(View view){
        id = id_txt.getText().toString();
        pwd = pwd_txt.getText().toString();

        final String url = MainActivity.url+"/AndLoginController";
        final ContentValues cv = new ContentValues();//키, 값으로 저장(맵의 일종)
        cv.put("id", id);
        cv.put("pwd", pwd);

        new Thread() {
            public void run() {
                String result = http.request(url, cv); //AndLoginController  웹의 응답을 받고
                try {
                    JSONObject obj = new JSONObject(result);
                    flag = obj.getBoolean("flag");
                    if(flag){
                        MainActivity.id = id; // true일 경우에 메인액티비에 담고
                    }
                    Bundle bun = new Bundle(); //맵과 동일
                    bun.putBoolean("res", flag);// 결과값을 담는다.
                    Message msg = handler.obtainMessage(); //메시지 큐와 같은 역활
                    msg.setData(bun);
                    handler.sendMessage(msg);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            boolean res = bun.getBoolean("res");
            if(res){
                Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
                //에물레이터에 있는 파일
                SharedPreferences pref = LoginActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);//현제 프로그램만 사용
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("id",id);
                edit.putString("pwd",pwd);
                edit.apply(); //프리퍼런스 파일에 작성
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);//인턴트를 통해서 메인 액티비티로 간다.
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
            }

        }
    };

}
