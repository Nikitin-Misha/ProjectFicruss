package com.company;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    private int x;//координата по Х
    private int y;//координата по У
    int k=0;//индикатор состояния панели
    int width;//ширина
    int height;//высота
    private PanelImage panelImage;//объект, включающий в себя 3 изображения панели, соответствующих разным состояниям

    Panel(int x, int y, int width, int height, PanelImage panelImage){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.panelImage = panelImage;
    }

    public void paint(Graphics g){
        //В зависимости от состояния панели рисуется одно из трёх изображений
        if (k==0){g.drawImage(panelImage.getImage1(),x,y,width,height,null);}
        if (k==1){g.drawImage(panelImage.getImage2(),x,y,width,height,null);}
        if (k==-1){g.drawImage(panelImage.getImage3(),x,y,width,height,null);}
    }


}

