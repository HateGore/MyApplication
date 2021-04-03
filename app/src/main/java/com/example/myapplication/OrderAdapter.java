package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order>{
    private ArrayList<Order> items;
    private Context context;
    private int resId;
    private Bitmap bm;
    private ImageView img;

    public OrderAdapter(Context context, int textViewResourceId,
                        ArrayList<Order> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        resId = textViewResourceId;
        this.items = objects;
    }

    // 어댑터 뷰에 데이터를 한 항목씩 출력하는 메서드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 한 항목을 출력할때 사용할 뷰가 있는지 확인하여 없으면 새로 생성
        // 이 프로그램에서는 폰북의 한 사람의 정보를 출력하는 뷰를

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(resId, null);
        }
        final View view = convertView;
        // 어댑터 뷰에 출력할 순서의 데이터를 m 변수에 저장
        final Order o = items.get(position);

        if (o != null) {
            TextView t1 = (TextView) view.findViewById(R.id.textView8);
            TextView t2 = (TextView) view.findViewById(R.id.textView9);
            TextView t3 = (TextView) view.findViewById(R.id.textView10);
            TextView t4 = (TextView) view.findViewById(R.id.textView11);

            // list_item.xml의 텍스트 뷰에 m객체의 name출력
            if (t1 != null) {
                t1.setText("상품명:"+o.getProd_name());
            }
            // list_item.xml의 텍스트 뷰에 m객체의 tel출력
            if (t2 != null) {
                t2.setText("구매일:"+o.getO_date());
            }
            if (t3 != null) {
                t3.setText("주문수량:"+o.getOrder_num());
            }
            if (t4 != null) {
                t4.setText("결제금액:"+o.getTotal_price());
            }
                img = (ImageView) view.findViewById(R.id.imageView3);
                new Thread() {
                    public void run() {
                        try {
                        URL url = new URL("http://118.128.215.216:8001" + o.getProd_img());
                        InputStream is = url.openStream();
                        bm = BitmapFactory.decodeStream(is);
                        }catch (MalformedURLException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }.start();

        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(bm);
        return view;
    }

}
