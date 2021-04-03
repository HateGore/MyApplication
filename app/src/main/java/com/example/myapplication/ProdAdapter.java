package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class ProdAdapter extends ArrayAdapter<Product>{
    private ArrayList<Product> items;
    private Context context;
    private int resId;
    private Bitmap bm;
    private ImageView img;

    public ProdAdapter(Context context, int textViewResourceId,
                         ArrayList<Product> objects) {
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
        final Product p = items.get(position);

        if (p != null) {
            TextView t1 = (TextView) view.findViewById(R.id.textView5);
            TextView t2 = (TextView) view.findViewById(R.id.textView6);

            // list_item.xml의 텍스트 뷰에 m객체의 name출력
            if (t1 != null) {
                t1.setText("상품명:"+p.getName());
            }
            // list_item.xml의 텍스트 뷰에 m객체의 tel출력
            if (t2 != null) {
                t2.setText("가격:"+p.getPrice());
            }

                img = (ImageView) view.findViewById(R.id.imageView);
                new Thread() {
                    public void run() {
                        //try {
                        // URL url = new URL(" https://5f8c88d3dbd6.ngrok.io/C:\\\\shopimg\\p" + p.getNum() + "\\" + p.getName() + ".png");
                        // https://5f8c88d3dbd6.ngrok.io/C:\\shopimg\p6\나비의자2.png
                            //    InputStream is = url.openStream();
                       //  bm = BitmapFactory.decodeStream(is);
                       // }catch (MalformedURLException e){
                       //     e.printStackTrace();
                       // }catch (IOException e){
                       //     e.printStackTrace();
                       // }
                    }
                }.start();

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(bm);
        return view;
    }

}
