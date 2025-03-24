package com.example.asteroid.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.asteroid.model.Asteroid;
import com.example.asteroid.R;

import java.util.List;

public class AsteroidAdapter extends RecyclerView.Adapter<AsteroidAdapter.ViewHolder> {
    private List<Asteroid> asteroidList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, info;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.asteroidName);
            info = v.findViewById(R.id.asteroidInfo);
        }
    }

    public AsteroidAdapter(List<Asteroid> list) {
        asteroidList = list;
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
    }

    @Override
    public int getItemCount() {
        return asteroidList.size();
    }
}
