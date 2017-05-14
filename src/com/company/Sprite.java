package com.company;

import java.awt.Graphics;
        import java.awt.Image;
class Sprite {

    private Image image;//изображение

     Sprite(Image image) {
        this.image = image;
    }

     void draw(Graphics g,int x,int y) {
        g.drawImage(image,x,y,null);
    }//изображение рисуется так, что у левого верхнего угла координаты Х и Y, ширина и высота как у картинки, переданной в assets

     void draw(Graphics g,int x,int y, int width, int height){
         g.drawImage(image,x,y,width,height,null);
     }//изображение рисуется так, что у левого верхнего угла координаты Х и Y, ширина и высота задаются вручную
}