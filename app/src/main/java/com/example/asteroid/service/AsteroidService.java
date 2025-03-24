package com.example.asteroid.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asteroid.model.Asteroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AsteroidService {

    public interface AsteroidCallback {
        void onSuccess(List<Asteroid> asteroidList);
        void onError(Exception e);
    }

    public void fetchAsteroids(Context context, String url, AsteroidCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        List<Asteroid> asteroidList = new ArrayList<>();
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

                        callback.onSuccess(asteroidList);

                    } catch (JSONException e) {
                        callback.onError(e);
                    }
                },
                error -> callback.onError(error)
        );

        queue.add(stringRequest);
    }
}
