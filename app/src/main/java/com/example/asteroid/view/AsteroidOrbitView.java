package com.example.asteroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AsteroidOrbitView extends View {

    private float asteroidDistanceKm = 500000f; // Distancia Tierra-Asteroide en km
    private final float earthOrbitKm = 149597870f; // Distancia Tierra-Sol

    private final float sunRadiusPx = 80f;
    private final float earthRadiusPx = 40f;
    private final float asteroidRadiusPx = 15f;

    private Paint paintSun, paintEarth, paintAsteroid, paintOrbit;

    public AsteroidOrbitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintSun = new Paint();
        paintSun.setColor(Color.YELLOW);
        paintSun.setStyle(Paint.Style.FILL);

        paintEarth = new Paint();
        paintEarth.setColor(Color.CYAN);
        paintEarth.setStyle(Paint.Style.FILL);

        paintAsteroid = new Paint();
        paintAsteroid.setColor(Color.RED);
        paintAsteroid.setStyle(Paint.Style.FILL);

        paintOrbit = new Paint();
        paintOrbit.setColor(Color.WHITE);
        paintOrbit.setStyle(Paint.Style.STROKE);
        paintOrbit.setStrokeWidth(2f);
    }

    public void setAsteroidDistance(float distanceKm) {
        this.asteroidDistanceKm = distanceKm;
        invalidate(); // Redibuja la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        // Escalado: del centro al borde disponible
        float maxEarthOrbitPx = Math.min(cx, cy) - sunRadiusPx - 40;
        float kmToPixel = maxEarthOrbitPx / earthOrbitKm;

        // ----------------------------------------
        // 🌞 Sol (en el centro)
        // ----------------------------------------
        canvas.drawCircle(cx, cy, sunRadiusPx, paintSun);

        // ----------------------------------------
        // 🌀 Órbita de la Tierra alrededor del Sol
        // ----------------------------------------
        float earthOrbitPx = earthOrbitKm * kmToPixel;
        canvas.drawCircle(cx, cy, earthOrbitPx, paintOrbit);

        // Posición de la Tierra a -45°
        double earthAngleRad = Math.toRadians(-45);
        float earthX = (float) (cx + earthOrbitPx * Math.cos(earthAngleRad));
        float earthY = (float) (cy + earthOrbitPx * Math.sin(earthAngleRad));
        canvas.drawCircle(earthX, earthY, earthRadiusPx, paintEarth);

        // ----------------------------------------
        // 🛰️ Órbita del asteroide alrededor de la Tierra
        // ----------------------------------------
        float asteroidOrbitPx = (float) (asteroidDistanceKm * kmToPixel * 1.2);
        canvas.drawCircle(earthX, earthY, asteroidOrbitPx, paintOrbit);

        // Posición del asteroide a 0° (derecha)
        float asteroidX = earthX + asteroidOrbitPx;
        float asteroidY = earthY;
        canvas.drawCircle(asteroidX, asteroidY, asteroidRadiusPx, paintAsteroid);
    }
}
