package com.valentin;

public class Vector {
    private double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector add(Vector vec) {
        return new Vector(this.x + vec.x, this.y + vec.y);
    }

    public Vector subtract(Vector vec) {
        return new Vector(this.x - vec.x, this.y - vec.y);
    }

    public Vector scale(double factor) {
        return new Vector(this.x * factor, this.y * factor);
    }
}
