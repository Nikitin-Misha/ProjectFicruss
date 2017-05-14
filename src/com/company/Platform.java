package com.company;


 class Platform extends object {

    private double cosalpha;//угол наклона платформы(к горизонту)
    private double width;//ширина платформы

    double getWidth() {
        return width;
    }

    void setWidth(double width) {
        this.width = width;
    }

    double getCosalpha() {
        return cosalpha;
    }

    void setCosalpha(double cosalpha) {
        this.cosalpha = cosalpha;
    }
}
