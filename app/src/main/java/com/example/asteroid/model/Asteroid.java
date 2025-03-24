package com.example.asteroid.model;

public class Asteroid {
    public String id;
    public String name;
    public double magnitude;
    public double distanceKm;

    public Asteroid(String id, String name, double magnitude, double distanceKm) {
        this.id = id;
        this.name = name;
        this.magnitude = magnitude;
        this.distanceKm = distanceKm;
    }
}

