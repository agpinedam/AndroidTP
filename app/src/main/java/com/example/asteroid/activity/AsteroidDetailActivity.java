package com.example.asteroid.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asteroid.R;
import com.example.asteroid.view.AsteroidOrbitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AsteroidDetailActivity extends AppCompatActivity {

    private TextView nameText, infoText, periodText;
    private AsteroidOrbitView orbitView;

    private final String API_KEY = "DEMO_KEY"; // ⚠️ Reemplazá si tenés tu propia API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroid_detail);

        nameText = findViewById(R.id.nameText);
        infoText = findViewById(R.id.infoText);
        periodText = findViewById(R.id.periodText);
        orbitView = findViewById(R.id.orbitView);

        String asteroidId = getIntent().getStringExtra("asteroid_id");

        if (asteroidId == null) {
            nameText.setText("ID no disponible");
            return;
        }

        String url = "https://api.nasa.gov/neo/rest/v1/neo/" + asteroidId + "?api_key=" + API_KEY;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);

                        String name = json.getString("name");
                        double magnitude = json.getDouble("absolute_magnitude_h");

                        JSONArray closeApproachData = json.getJSONArray("close_approach_data");
                        JSONObject missDistanceObj = closeApproachData
                                .getJSONObject(0)
                                .getJSONObject("miss_distance");

                        double distance = Double.parseDouble(missDistanceObj.getString("kilometers"));

                        JSONObject orbitalData = json.getJSONObject("orbital_data");
                        String orbitalPeriod = orbitalData.getString("orbital_period");

                        // Mostrar datos
                        nameText.setText(name);
                        infoText.setText("Magnitude : " + magnitude + "     Distance : " + (int) distance);
                        periodText.setText("Période orbitale : " + orbitalPeriod);

                        // Actualizar gráfico con distancia orbital
                        orbitView.setAsteroidDistance((float) distance);

                    } catch (JSONException e) {
                        nameText.setText("Erreur lors du traitement des données");
                        e.printStackTrace();
                    }
                },
                error -> {
                    nameText.setText("Erreur de connexion à l'API");
                    error.printStackTrace();
                });

        queue.add(request);
    }
}
