package com.example.internproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class second extends AppCompatActivity {
    ImageView imageView1;
    TextView author1;
    TextView title1;
    RequestQueue mqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView1=findViewById(R.id.image);
        author1=findViewById(R.id.author);
        title1=findViewById(R.id.title);

        int a=getIntent().getIntExtra("position",-1);

        mqueue= Volley.newRequestQueue(this);
        jsonparse(a);



    }


    public void jsonparse(final int a)
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

                                if(a==i)
                                {
                                    title1.setText(title);
                                    author1.setText(author);
                                    Picasso.get().load(image).into(imageView1);
                                }





                            }

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
