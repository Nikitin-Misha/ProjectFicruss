package com.company;


 class Spike extends object{
    private int xs;//координата острия шипа по Х
    private int ys;//координата острия шипа по У
     Spike(int x,int y,int xs,int ys){
        this.setXs(xs);
        this.setYs(ys);
        this.setX(x);
        this.setY(y);
    }

    int getXs() {
        return xs;
    }

    void setXs(int xs) {
        this.xs = xs;
    }

    int getYs() {
        return ys;
    }

    void setYs(int ys) {
        this.ys = ys;
    }
}
