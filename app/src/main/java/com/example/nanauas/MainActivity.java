package com.example.nanauas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView txtJSON;
    private Button btnJSON;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        txtJSON = findViewById(R.id.txtJSON);
        btnJSON = findViewById(R.id.btnJSON);

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uraiJson();
            }
        });
    }


    private void uraiJson() {
        String url = "http://192.168.5.226/rest-api/uasjson/show.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("kegemaran");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject mahasantri = jsonArray.getJSONObject(i);
                                String id = mahasantri.getString("id");
                                String nama = mahasantri.getString("mahasantri_id");
                                String hobby = mahasantri.getString("hobby");

                                txtJSON.append(
                                        id+", "+nama+", "+hobby+"\n\n"
                                );
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Erorr", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }
}