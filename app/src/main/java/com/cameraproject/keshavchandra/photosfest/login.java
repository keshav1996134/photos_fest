package com.cameraproject.keshavchandra.photosfest;

/**
 * Created by Keshav Chandra on 29-10-2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;


public class Login extends Activity {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    SharedPreferences sharedPreferences;
    public static RequestQueue rq;
    int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        sharedPreferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);

        final EditText email = (EditText) findViewById(R.id.et_email);
        final EditText password = (EditText) findViewById(R.id.et_password);
        //final String userLogin = email.getText().toString();
        //final String passwordLogin = password.getText().toString();
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        final Button login = (Button) findViewById(R.id.btnLogin);
        /*login.setVisibility(View.INVISIBLE);

        if(email.length()>0 && password.length()>0){
            login.setVisibility(View.VISIBLE);
        }else{
            login.setVisibility(View.INVISIBLE);
        }*/
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rq = Volley.newRequestQueue(getApplicationContext());
                String url = AppConfig.IP2 + "login.php";
                String url2 = AppConfig.IP2 + "likeimage.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(KEY_USERNAME,email.getText().toString());
                        params.put(KEY_PASSWORD,password.getText().toString());
                        return params;
                    }

                };

                System.out.println("Hello Hello "+email.getText().toString());
                RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                requestQueue.add(stringRequest);


                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        int i;
                        for (i = 0; i < jsonArray.length(); i++) {
                            try {

                                int f = 0;
                                final String user = jsonArray.getJSONObject(i).getString("username");
                                final String pass = jsonArray.getJSONObject(i).getString("password");
                                System.out.println("Hello" + email.getText().toString());
                                if (user.equals(email.getText().toString()) && pass.equals(password.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    RequestParams p = new RequestParams();
                                    p.put("user_name",email.getText().toString());
                                    p.put("file_name",password.getText().toString());
                                    editor.putString("nameKey", user);
                                    editor.putString("passKey", pass);

                                    editor.commit();

                                    Intent intent = new Intent(getApplicationContext(),home.class);
                                    intent.putExtra("User",user);
                                    startActivity(intent);
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                    }
                }
            }

            ,new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse (VolleyError volleyError){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            }

            );


            rq.add(jsonArrayRequest);
               /* Toast.makeText(getApplicationContext(), "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(),Login.class);
                startActivity(i);*/

        }
    });

    }


}



/*
{
protected Map<String,String> getParams(){
        Map<String,String> params = new HashMap<String, String>();
        params.put(KEY_USERNAME,user);
        params.put(KEY_PASSWORD,pass);
        return params;
        }
        }*/



          /*      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        for(int i=0;i<jsonObject.length();i++){
                            try {
                                if(jsonObject.getString(String.valueOf(i))==){

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });*/