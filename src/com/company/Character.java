package com.company;

class Character extends object {
private int cnt;//количество патронов
private double R;//радиус
private int life;//количество жизней

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    double getR() {
        return R;
    }

     int getCnt() {
        return cnt;
    }

     void setCnt(int cnt) {
        this.cnt = cnt;
    }

     void setR(double r) {
        R = r;
    }
     void reduceByOne(){
        this.cnt -=1;
        this.m -= 0.25;
    }
}
