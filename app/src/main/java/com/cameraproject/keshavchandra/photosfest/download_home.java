package com.cameraproject.keshavchandra.photosfest;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class download_home extends Activity {
    List<ImageItem> imageItems=new ArrayList<ImageItem>();
    ImageAdapter imageAdapter;
    static String[] imageNames;
    int[] likes;
    int[] dislikes;
    ListView listView;
    public static RequestQueue rq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_home);
        listView=(ListView)findViewById(R.id.listView);
        //listView.setClickable(false);
        //listView.setFocusable(false);
        listView.setItemsCanFocus(true);

//        int loader = R.drawable.loader;

        // Imageview to show
        final ImageView image = (ImageView) findViewById(R.id.image);
        rq= Volley.newRequestQueue(this);
        String url=AppConfig.IP+"download_images.php";
        System.out.println(url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
            public void onResponse(JSONObject jsonObject) {
                    imageNames=new String[jsonObject.length()/3];
                    likes=new int[jsonObject.length()/3];
                    dislikes=new int[jsonObject.length()/3];
                for(int i=0;i<jsonObject.length();i++){
                    try {
                        imageNames[i]=jsonObject.getString("name"+i);
                        likes[i]=jsonObject.getInt("likes" + i);
                        dislikes[i]=jsonObject.getInt("dislikes" + i);
                        imageItems.add(new ImageItem(imageNames[i],likes[i],dislikes[i],null));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                    next();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jsonObjectRequest);


        // Image url
       /* final String image_url = AppConfig.IP+"Temp/image1.jpg";
        ImageRequest im=new ImageRequest(image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                System.out.println(image_url);
                image.setImageBitmap(bitmap);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(im);*/
        // ImageLoader class instance
//        ImageLoader imgLoader = new ImageLoader(getBaseContext());

        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
//       imgLoader.DisplayImage(image_url, loader, image);
    }
public void next(){
    for(int i=0;i<imageNames.length;i++) {
        final int k=i;
        final String image_url = AppConfig.IP + "Temp/" + download_home.imageNames[i];
        System.out.println(image_url);
        ImageRequest im = new ImageRequest(image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageItems.get(k).setBitmap(bitmap);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        download_home.rq.add(im);
    }

    imageAdapter=new ImageAdapter(getApplicationContext(),0,imageItems);
    listView.setAdapter(imageAdapter);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_download_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
