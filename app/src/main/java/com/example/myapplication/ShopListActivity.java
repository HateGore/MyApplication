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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Product> list = new ArrayList<>();
    private ProdAdapter adapter;
    private RequestHttpURLConnection http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        listView = findViewById(R.id.lv_main);
        http = new RequestHttpURLConnection();

        init();

        // 리스트 아이템 선택하면 해당 항목에 대한 내용 출력
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShopListActivity.this, ShopItemActivity.class);
                intent.putExtra("p", list.get(position));
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);
    }

    public void init() {
        final String url = MainActivity.url+"/shop2/AndProdController";
        final ContentValues cv = new ContentValues();

        new Thread() {
            public void run() {
                String result = http.request(url, cv);

                    Bundle bun = new Bundle();
                    bun.putString("res", result);
                    Message msg = handler.obtainMessage();
                    msg.setData(bun);
                    handler.sendMessage(msg);

            }
        }.start();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String result = bun.getString("res");
            try {
                JSONArray arr = new JSONArray(result);
                for(int i=0;i<arr.length();i++){
                    JSONObject obj = arr.getJSONObject(i);
                    list.add(new Product(
                            obj.getInt("num"),
                            obj.getString("name"),
                            obj.getString("maker"),
                            obj.getInt("price"),
                            obj.getString("origin"),
                            obj.getString("material"),
                            obj.getInt("quantity"),
                            obj.getString("imgPath"),
                            obj.getInt("category1"),
                            obj.getInt("category2"),
                            obj.getInt("event_num")
                    ));
                }
                adapter = new ProdAdapter(ShopListActivity.this, R.layout.pord_list_item_layout, list);
                //리스트 뷰에 어댑터 설정
                Toast.makeText(getApplicationContext(),list.get(0).toString(),Toast.LENGTH_SHORT).show();
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);
            }catch (JSONException e){
                e.printStackTrace();
            }catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
    };
}
