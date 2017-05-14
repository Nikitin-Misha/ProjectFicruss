package com.company;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

    class PanelImage {
        private Image image1;
        private Image image2;
        private Image image3;
         PanelImage(String image1, String image2, String image3){
            URL imgURL = Panel.class.getResource(image1);//по названию изображения(String) получаем само изображение и передаём его в image
            this.image1 = new ImageIcon(imgURL).getImage();
            imgURL = Panel.class.getResource(image2);
            this.image2 = new ImageIcon(imgURL).getImage();
            imgURL = Panel.class.getResource(image3);
            this.image3 = new ImageIcon(imgURL).getImage();
        }
        Image getImage1(){
            return image1;

        }
        Image getImage2(){
            return image2;

        }
        Image getImage3(){
            return image3;

        }

    }

