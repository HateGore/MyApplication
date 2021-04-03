package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Order> list = new ArrayList<>();
    private OrderAdapter adapter;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        listView = findViewById(R.id.lv2);
        title = findViewById(R.id.textView12);
        Intent intent = getIntent();
        String res = intent.getStringExtra("res");
        makeJson(res);
    }

    public void makeJson(String res){
        int type=0;
        try {
            JSONArray arr = new JSONArray(res);
            for(int i=0;i<arr.length();i++){
                JSONObject obj = arr.getJSONObject(i);
                Order x = new Order(obj.getInt("num"), obj.getInt("pro_num"), obj.getInt("order_num"), obj.getInt("total_price"),
                        obj.getString("o_id"), obj.getString("o_date"), obj.getInt("o_state"), obj.getInt("d_state"));
                x.setProd_name(obj.getString("prod_name"));
                x.setProd_img(obj.getString("prod_img"));
                list.add(x);
            }
            type=list.get(list.size()-1).getO_state();
            adapter = new OrderAdapter(OrderListActivity.this, R.layout.order_list_item, list);
            //리스트 뷰에 어댑터 설정
            listView.setAdapter(adapter);
            if(type==0){
                title.setText("구매목록");
            }else{
                title.setText("장바구니 목록");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
