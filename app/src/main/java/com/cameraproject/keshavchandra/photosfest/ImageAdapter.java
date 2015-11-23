package com.cameraproject.keshavchandra.photosfest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cameraproject.keshavchandra.photosfest.R.drawable.liked;

/**
 * Created by Keshav Chandra on 01-10-2015.
 */

public class ImageAdapter extends ArrayAdapter {
    List<ImageItem> imageItems=new ArrayList<ImageItem>();
    Context context;
    RequestParams params = new RequestParams();

    public ImageAdapter(Context context, int resource,List<ImageItem> imageItems) {
        super(context, resource,imageItems);
        this.imageItems=imageItems;
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
       View v=null;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=vi.inflate(R.layout.pic_item, null);
        final ImageView main=(ImageView)v.findViewById(R.id.iv_main);
        final ImageButton like=(ImageButton)v.findViewById(R.id.ib_like);
        ImageButton dislike=(ImageButton)v.findViewById(R.id.ib_dislike);
        final TextView like_count=(TextView)v.findViewById(R.id.tv_like_count);
        final TextView dislike_count=(TextView)v.findViewById(R.id.tv_dislike_count);
        final ImageItem o=imageItems.get(position);
        String s=Integer.toString(o.like_count);
        like_count.setText(s.toCharArray(),0,s.length());
        s=Integer.toString(o.dislike_count);
        dislike_count.setText(s.toCharArray(), 0, s.length());
        main.setImageBitmap(o.bitmap);
        //like.setClickable(true);
        //like.setFocusable(false);
        dislike.setClickable(true);
        dislike.setFocusable(false);
        //like.setTag(R.drawable.like);
        //dislike.setTag(R.drawable.dislike);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setClickable(false);
                like.setFocusable(false);
                System.out.println("clicked" + like.getTag());
                final String nam = o.image_name;
                if (like.getTag().equals("unclicked")) {
                    String url = AppConfig.IP + "liked.php?name=" + nam;
                    String url2 = AppConfig.IP2 + "likeimage.php?name=" + nam;
                    System.out.println(url);
                    StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            like.setClickable(true);
                            like.setFocusable(true);
                            like.setImageResource(liked);
                            like.setTag("clicked");
                            int ct = Integer.parseInt(like_count.getText().toString());
                            ct++;
                            String ss = Integer.toString(ct);
                            like_count.setText(ss);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            like.setClickable(true);
                            like.setFocusable(true);
                        }
                    });
                    download_home.rq.add(sr);

                    StringRequest sr2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Toast.makeText(getContext(), s , Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }){
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("image_name",nam);

                        return params;
                    }};
                    download_home.rq.add(sr2);




                } else if (like.getTag().equals("clicked")) {
                    String url = AppConfig.IP + "disliked.php?name="+nam;
                    System.out.println(url);
                    StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            like.setClickable(true);
                            like.setFocusable(true);
                            like.setImageResource(R.drawable.like);
                            like.setTag("unclicked");
                            int ct = Integer.parseInt(like_count.getText().toString());
                            ct--;
                            System.out.println("clicked " + ct);
                            String ss = Integer.toString(ct);
                            like_count.setText(ss);
                            //like_count.setText(ss.toCharArray(), 0, ss.length());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            like.setClickable(true);
                            like.setFocusable(true);
                        }
                    });
                    download_home.rq.add(sr);
                }
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }
}
