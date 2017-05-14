package com.company;

 class Projectile extends object {
    private double r;//радиус снаряда
    private int k=0;//флаг, показывающий попал ли снаряд в character

     int getK() {
         return k;
     }

     void setK(int k) {
         this.k = k;
     }

     double getR() {
        return r;
     }

     void setR(double r) {
        this.r = r;
     }
}
