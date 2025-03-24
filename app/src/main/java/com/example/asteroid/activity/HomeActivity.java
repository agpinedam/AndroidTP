package com.example.asteroid.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asteroid.R;
import com.example.asteroid.adapter.AsteroidAdapter;
import com.example.asteroid.model.Asteroid;
import com.example.asteroid.service.AsteroidService;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView asteroidCount;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Referencias de la vista
        asteroidCount = findViewById(R.id.asteroidCount);
        recyclerView = findViewById(R.id.recyclerView);

        // Layout de la lista
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Línea entre cada ítem
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        recyclerView.addItemDecoration(divider);

        // URL de la API de asteroides
        String url = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=sFgXWnjs1blKetFfycX1CmzPxfAIIRKhSeNgarVe";

        // Llamada al servicio
        AsteroidService service = new AsteroidService();
        service.fetchAsteroids(this, url, new AsteroidService.AsteroidCallback() {
            @Override
            public void onSuccess(List<Asteroid> asteroidList) {
                asteroidCount.setText("Nombre d'astéroïdes : " + asteroidList.size());
                recyclerView.setAdapter(new AsteroidAdapter(HomeActivity.this, asteroidList));
            }

            @Override
            public void onError(Exception e) {
                asteroidCount.setText("Erreur de chargement des données");
                e.printStackTrace();
            }
        });
    }
}
