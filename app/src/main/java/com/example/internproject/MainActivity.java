package com.example.internproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    RequestQueue mqueue;
    ArrayList<String>ti;
    ArrayList<String>au;
    ArrayList<String>im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list);
        ti=new ArrayList<>();
        au=new ArrayList<>();
        im=new ArrayList<>();



        mqueue= Volley.newRequestQueue(this);
        jsonparse();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MainActivity.this,second.class);
                i.putExtra("position",position);
                startActivity(i);
            }
        });







    }

    public void jsonparse()
    {
        String url="http://www.mocky.io/v2/5cc008de310000440a035fdf";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("book_array");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String title=jsonObject.getString("book_title");
                                String image=jsonObject.getString("image");
                                String author=jsonObject.getString("author");

                                im.add(image);
                                ti.add(title);
                                au.add(author);



                            }
                            MyListAdapter adapter=new MyListAdapter(MainActivity.this, ti, au,im);

                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("error hai",error.toString());

            }
        });
        mqueue.add(request);
    }

}
