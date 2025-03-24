package com.example.asteroid.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.asteroid.activity.AsteroidDetailActivity;
import com.example.asteroid.model.Asteroid;
import com.example.asteroid.R;

import java.util.List;

public class AsteroidAdapter extends RecyclerView.Adapter<AsteroidAdapter.ViewHolder> {
    private List<Asteroid> asteroidList;
    private Context context;

    public AsteroidAdapter(Context context, List<Asteroid> list) {
        this.context = context;
        asteroidList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, info;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.asteroidName);
            info = v.findViewById(R.id.asteroidInfo);
        }
    }

    @Override
    public AsteroidAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asteroid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Asteroid a = asteroidList.get(position);
        holder.name.setText("(" + a.name + ")");
        holder.info.setText("Magnitude: " + a.magnitude + "    Distance: " + (int)a.distanceKm + " km");

        // CLIC EN ÍTEM
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, AsteroidDetailActivity.class);
            intent.putExtra("asteroid_id", a.id);
            intent.putExtra("asteroid_name", a.name);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return asteroidList.size();
    }
}
