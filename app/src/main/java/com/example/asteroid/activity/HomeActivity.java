package com.example.asteroid.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asteroid.R;
import com.example.asteroid.adapter.AsteroidAdapter;
import com.example.asteroid.model.Asteroid;
import com.example.asteroid.service.AsteroidService;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView asteroidCount;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        asteroidCount = findViewById(R.id.asteroidCount);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=sFgXWnjs1blKetFfycX1CmzPxfAIIRKhSeNgarVe";

        AsteroidService service = new AsteroidService();
        service.fetchAsteroids(this, url, new AsteroidService.AsteroidCallback() {
            @Override
            public void onSuccess(List<Asteroid> asteroidList) {
                asteroidCount.setText("Nombre d'astéroïdes : " + asteroidList.size());
                recyclerView.setAdapter(new AsteroidAdapter(asteroidList));
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                asteroidCount.setText("Erreur de chargement des données");
            }
        });
    }
}
