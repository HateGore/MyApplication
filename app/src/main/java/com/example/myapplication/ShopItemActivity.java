package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ShopItemActivity extends AppCompatActivity {
    private TextView name_tv;
    private TextView price_tv;
    private TextView content_tv;
    private EditText or_num_et;
    private ImageView img;
    private Bitmap bm;
    private Product p;
    private RequestHttpURLConnection http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item);
        name_tv = findViewById(R.id.textView2);
        price_tv = findViewById(R.id.textView3);
        content_tv = findViewById(R.id.textView4);
        img = findViewById(R.id.imageView2);
        or_num_et = findViewById(R.id.editText3);
        http = new RequestHttpURLConnection();
        Intent intent = getIntent();
        p = (Product) intent.getSerializableExtra("p");
        name_tv.setText("상품명: "+p.getName());
        price_tv.setText("단가: "+p.getPrice());
        content_tv.setText("상품설명: "+p.getMaker() + ", " + p.getMaterial());
        new Thread() {
            public void run() {
                try {
                    URL url = new URL("http://118.128.215.216:8001" + p.getImgPath());
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Bundle bun = new Bundle();
                    bun.putBoolean("res", true);
                    Message msg = handler.obtainMessage();
                    msg.setData(bun);
                    handler.sendMessage(msg);

                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
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
                img.setImageBitmap(bm);
            }
        }
    };

    public void ondelay(View view){
        proc(1);
    }

    public void ondirect(View view){
        proc(0);
    }
    public void proc(int type){
        if(MainActivity.id==""){
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        String pwd = pref.getString("pwd","");
        String n = or_num_et.getText().toString();
        int order_num = Integer.parseInt(n);
        int total_price = p.getPrice()*order_num;
        final String url = MainActivity.url+"/AndOrderController";
        final ContentValues cv = new ContentValues();
        cv.put("id", MainActivity.id);
        cv.put("pwd", pwd);
        cv.put("o_state", type);
        cv.put("prod_num", p.getNum());
        cv.put("order_num", order_num);
        cv.put("total_price", total_price);

        new Thread() {
            public void run() {
                String result = http.request(url, cv);
                Bundle bun = new Bundle();
                bun.putString("res", result);
                Message msg = handler2.obtainMessage();
                msg.setData(bun);
                handler2.sendMessage(msg);
            }
        }.start();
    }
    Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String result = bun.getString("res");
            Intent intent = new Intent(ShopItemActivity.this, OrderListActivity.class);
            intent.putExtra("res", result);
            startActivity(intent);
        }
    };
}
