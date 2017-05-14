package com.company;

class object {

    private int x;//координата по X центра объекта
    private int y;//координата по Y центра объекта
    double m;//масса данного объекта
    private double vx;//скорость в данный момент времени по Х
    private double vy;//скорость в данный момент времени по Y
    private double ax;//ускорение в данный момент времени по X
    private double ay;//ускорение в данный момент времени по Y

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    double getM() {
        return m;
    }

    void setM(double m) {
        this.m = m;
    }

    double getVx() {
        return vx;
    }

    void setVx(double vx) {
        this.vx = vx;
    }

    double getVy() {
        return vy;
    }

    void setVy(double vy) {
        this.vy = vy;
    }

    double getAx() {
        return ax;
    }

    void setAx(double ax) {
        this.ax = ax;
    }

    double getAy() {
        return ay;
    }

    void setAy(double ay) {
        this.ay = ay;
    }
}