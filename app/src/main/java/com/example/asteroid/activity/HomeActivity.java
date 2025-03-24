package com.example.asteroid.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asteroid.model.Asteroid;
import com.example.asteroid.service.AsteroidAdapter;
import com.example.asteroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView asteroidCount;
    RecyclerView recyclerView;
    List<Asteroid> asteroidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        asteroidCount = findViewById(R.id.asteroidCount);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=sFgXWnjs1blKetFfycX1CmzPxfAIIRKhSeNgarVe";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONObject neos = json.getJSONObject("near_earth_objects");
                        JSONArray asteroids = neos.getJSONArray("2015-09-07");

                        for (int i = 0; i < asteroids.length(); i++) {
                            JSONObject asteroidObj = asteroids.getJSONObject(i);
                            String name = asteroidObj.getString("name");
                            double magnitude = asteroidObj.getDouble("absolute_magnitude_h");

                            JSONArray approachData = asteroidObj.getJSONArray("close_approach_data");
                            for (int j = 0; j < approachData.length(); j++) {
                                JSONObject approach = approachData.getJSONObject(j);
                                if (approach.getString("orbiting_body").equals("Earth")) {
                                    String kmStr = approach.getJSONObject("miss_distance").getString("kilometers");
                                    double distance = Double.parseDouble(kmStr);
                                    asteroidList.add(new Asteroid(name, magnitude, distance));
                                    break;
                                }
                            }
                        }

                        asteroidCount.setText("Nombre d'astéroïdes : " + asteroidList.size());
                        recyclerView.setAdapter(new AsteroidAdapter(asteroidList));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        queue.add(stringRequest);
    }
}
