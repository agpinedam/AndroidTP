package com.example.asteroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AsteroidOrbitView extends View {

    private float asteroidDistanceKm = 500000f;
    private int asteroidPeriodDays = 1000;

    private final float earthOrbitKm = 149597870f;

    private final float sunRadiusPx = 80f;
    private final float earthRadiusPx = 50f;
    private final float asteroidRadiusPx = 25f;

    private Paint paintSun, paintEarth, paintAsteroid, paintOrbit;

    private long lastFrameTime = 0;
    private float earthAngle = 0f;
    private float asteroidAngle = 0f;

    private final int EARTH_ORBIT_DURATION_MS = 10000;

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

        startAnimationLoop();
    }

    public void setAsteroidDistance(float distanceKm) {
        this.asteroidDistanceKm = distanceKm;
    }

    public void setAsteroidPeriod(float periodDays) {
        this.asteroidPeriodDays = Math.max(1, Math.round(periodDays));
    }

    private void startAnimationLoop() {
        lastFrameTime = System.currentTimeMillis();
        postOnAnimation(animationRunnable);
    }

    private final Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {
            long now = System.currentTimeMillis();

            if (lastFrameTime != 0) {
                long delta = now - lastFrameTime;

                float earthDegPerMs = 360f / EARTH_ORBIT_DURATION_MS;
                earthAngle = (earthAngle + earthDegPerMs * delta) % 360;

                float astroDegPerMs = earthDegPerMs * (365f / asteroidPeriodDays);
                asteroidAngle = (asteroidAngle + astroDegPerMs * delta) % 360;
            }

            lastFrameTime = now;
            invalidate();
            postOnAnimation(this);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        float maxEarthOrbitPx = Math.min(cx, cy) - sunRadiusPx - 40;
        float kmToPixel = maxEarthOrbitPx / earthOrbitKm;

        canvas.drawCircle(cx, cy, sunRadiusPx, paintSun);

        float earthOrbitPx = earthOrbitKm * kmToPixel;
        canvas.drawCircle(cx, cy, earthOrbitPx, paintOrbit);

        double earthRad = Math.toRadians(earthAngle);
        float earthX = (float) (cx + earthOrbitPx * Math.cos(earthRad));
        float earthY = (float) (cy + earthOrbitPx * Math.sin(earthRad));
        canvas.drawCircle(earthX, earthY, earthRadiusPx, paintEarth);

        float asteroidOrbitPx = asteroidDistanceKm * kmToPixel;
        canvas.drawCircle(earthX, earthY, asteroidOrbitPx, paintOrbit);

        double asteroidRad = Math.toRadians(asteroidAngle);
        float asteroidX = (float) (earthX + asteroidOrbitPx * Math.cos(asteroidRad));
        float asteroidY = (float) (earthY + asteroidOrbitPx * Math.sin(asteroidRad));
        canvas.drawCircle(asteroidX, asteroidY, asteroidRadiusPx, paintAsteroid);
    }
}
