package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.net.URL;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {
    //Получение размеров экрана
    private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private static int WIDTH = dim.width;
    private static int HEIGHT = dim.height;
    private boolean running;//флаг, показывающий, выполняется ли game
    private Character character1 = new Character();//1 герой(правый; за него играет player2)
    private Character character2 = new Character();//2 герой(левый; за него играет player1)
    //Платформы
    private Platform platform1 = new Platform();
    private Platform platform2 = new Platform();
    private Platform platform3 = new Platform();
    private Platform platform4 = new Platform();
    private Platform platform5 = new Platform();
    private Platform platform6 = new Platform();
    //ArrayList пуль(массив переменной длины)
    private static ArrayList<Projectile> projectiles1 = new ArrayList<Projectile>();
    private static ArrayList<Projectile> projectiles2 = new ArrayList<Projectile>();
    private Spike []arr = new Spike[111];//массив шипов

    //флаги для обработки нажатий на соответствующие клавиши
    private boolean leftPressed = false;
    private boolean dPressed = false;
    private boolean aPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean wPressed = false;
    private boolean fPressed = false;
    private boolean ctrlPressed = false;

    //флаги для определения того, какого персонажа выбрал пользователь, и их сеттеры
    private int N1=0;
    void setN1(int n1) {
        N1 = n1;
    }

    private int N2=0;
    void setN2(int n2) {
        N2 = n2;
    }

    //время (для рисования анимации)
    private double time1 =40;
    private double time2 =40;

    //Флаги, показывающие, выиграл ли соответствующий игрок
    private boolean Player1win = false;
    private boolean Player2win = false;

    //Спрайты для всех объектов, которые должны быть нарисованы
    private Sprite []Scharacter1arr = new Sprite[9];
    private Sprite []Scharacter2arr = new Sprite[9];
    private Sprite Splatform1;
    private Sprite Splatform2;
    private Sprite Splatform3;
    private Sprite Splatform4;
    private Sprite Splatform5;
    private Sprite Splatform6;
    private Sprite Sbackground;
    private Sprite Sground;
    private Sprite Sspike;
    private Sprite Sspike2;
    private Sprite Srightspike;
    private Sprite Srightspike2;
    private Sprite Supspike;
    private Sprite Supspike2;
    private Sprite Sboom1;
    private Sprite Sboom2;
    private Sprite Splayer1wins;
    private Sprite Splayer2wins;
    private Sprite Sprojectile1;
    private Sprite Sprojectile2;
    private Sprite Slifeline1;
    private Sprite Slifeline2;

    //Геттеры для Player1win и Player2win
    boolean isPlayer1win() {
        return Player1win;
    }
    boolean isPlayer2win() {
        return Player2win;
    }

    void start() {
        running = true;
        new Thread(this).start();//создаём новый поток и запускаем его
        //метод start() выполняет вызов метода run()
        //this - вызываем метод run() нашего класса
    }

    public void run() {
        long lastTime = System.currentTimeMillis();//получаем текущее время
        long delta;
        while(running) {
            delta = System.currentTimeMillis() - lastTime;//из текущего времени вычитаем время,
            // которое было текущим во время предыдущего выполнения цикла - получаем изменение времени - delta
            lastTime = System.currentTimeMillis();
            render();//перерисовываем game(за промежуток времени delta некоторые объекты поменяли местоположение - рисуем их на новом месте )
            update(delta);//обновляем game (за промежуток времени delta некоторые объекты поменяли
            // местоположение, скорости, массы и т.п.(в результате взаимодействия) - изменяем координаты, скорости и т.п.)
        }

    }

    //рестарт - герои возвращаются на свои места, кол-во патронов и кол-во жизней обновляется
    void restart() {
        Player1win = false;
        Player2win = false;
        character1.setCnt(20);
        character2.setCnt(20);
        character1.setM(10);
        character2.setM(10);
        character1.setX(WIDTH/4*3);
        character1.setVx(0);
        character1.setAx(0);
        character1.setAy(0);
        character1.setVy(0);
        character1.setY(0);
        character2.setX(WIDTH/4);
        character2.setY(0);
        character2.setVx(0);
        character2.setAx(0);
        character2.setAy(0);
        character2.setVy(0);
        character1.setLife((int)(0.4399707*WIDTH));
        character2.setLife((int)(0.4399707*WIDTH));
        //После рестарта пули, которые остались на карте, не наносят урон
        for (int i=0;i<projectiles2.size();i++){
            projectiles2.get(i).setK(1);
        }
        for (int i=0;i<projectiles1.size();i++){
            projectiles1.get(i).setK(1);
        }
        running = true;
    }

    //Инициализация - у всех объектов задаются координаты, масса, скорость и т.п.
    void init()
    {
        character1.setCnt(20);
        character2.setCnt(20);
        int n = 0;
        //Задаются координаты шипов
        for (int i=0;i<15;i++){
            arr[i] = new Spike(0,n,85,n+HEIGHT/30);
            n+=HEIGHT/15;
        }
        n=0;
        for (int i=15;i<30;i++){
            arr[i] = new Spike(0,n+30,85,n+30+HEIGHT/30);
            n+=HEIGHT/15;
        }
        n=0;
        for (int i=30;i<45;i++){
            arr[i] = new Spike(WIDTH-85,n,WIDTH-85,n+HEIGHT/30);
            n+=HEIGHT/15;
        }
        n=0;
        for (int i=45;i<60;i++){
            arr[i] = new Spike(WIDTH-85,n+30,WIDTH-85,n+30+HEIGHT/30);
            n+=HEIGHT/15;
        }
        n=0;
        for (int i=60;i<111;i++){
            arr[i] = new Spike(n,HEIGHT-85,WIDTH/102+n,HEIGHT-85);
            n+=WIDTH/51;
        }
        if (N1==1){
            character1.setR(32);}
        if (N1==2){
            character1.setR(30);}
        if (N1==3){
            character1.setR(35);}
        if (N2==1){
            character2.setR(32);}
        if (N2==2){
            character2.setR(30);}
        if (N2==3){
            character2.setR(35);}
        character1.setLife((int)(0.4399707*WIDTH));
        character2.setLife((int)(0.4399707*WIDTH));
        character1.setM(10);
        character2.setM(10);
        character1.setAx(0);
        character2.setAx(0);
        character1.setAy(0);
        character2.setAy(0);
        character1.setVy(0);
        character2.setVy(0);
        character1.setX(WIDTH/4*3);
        character1.setY(0);
        character2.setX(WIDTH/4);
        character2.setY(0);
        platform1.setX(WIDTH/2);
        platform1.setY((int)(HEIGHT/4+HEIGHT/6.2+HEIGHT/3));
        platform1.setM(1000000000);
        platform1.setVy(0);
        platform1.setVx(0);
        platform1.setCosalpha(1.0);
        platform1.setWidth(WIDTH*0.166);
        platform2.setX(WIDTH/4*3);
        platform2.setY(HEIGHT/4*3+HEIGHT/3);
        platform2.setM(1000000000);
        platform2.setVy(0);
        platform2.setVx(0);
        platform2.setCosalpha(1.0);
        platform2.setWidth(WIDTH*0.166);
        platform3.setX((int)(WIDTH*0.61));
        platform3.setY(HEIGHT/4+HEIGHT/13+HEIGHT/3);
        platform3.setM(1000000000);
        platform3.setVy(0);
        platform3.setVx(0);
        platform3.setCosalpha(0.8660254);
        platform3.setWidth(WIDTH*0.166);
        platform4.setX((int)(WIDTH*0.39));
        platform4.setY(HEIGHT/4+HEIGHT/13+HEIGHT/3);
        platform4.setM(1000000000);
        platform4.setVy(0);
        platform4.setVx(0);
        platform4.setCosalpha(-0.8660254);
        platform4.setWidth(WIDTH*0.166);
        platform5.setX(WIDTH/4+WIDTH/722);
        platform5.setY(HEIGHT/4+HEIGHT/3);
        platform5.setM(1000000000);
        platform5.setVy(0);
        platform5.setVx(0);
        platform5.setCosalpha(1.0);
        platform5.setWidth(WIDTH*0.166);
        platform6.setX(WIDTH/4*3-WIDTH/722);
        platform6.setY(HEIGHT/4+HEIGHT/3);
        platform6.setM(1000000000);
        platform6.setVy(0);
        platform6.setVx(0);
        platform6.setCosalpha(1.0);
        platform6.setWidth(WIDTH*0.166);
        //game добавляется слушатель клавиатуры
        addKeyListener(new KeyInputHandler());
        if (N1==2){
            Sboom1 = getSprite("assets/alienboom.png");
            for (int i=0;i<9;i++){
                 Sprojectile1 = getSprite("assets/projectile.png");
                 Scharacter1arr[i] = getSprite("assets/ficrus"+(i+1)+".png");}}
        if (N1==3){
            Sboom1 = getSprite("assets/reksboom.png");
            for (int i=0;i<9;i++){
                Sprojectile1 = getSprite("assets/projectile2.png");
                Scharacter1arr[i] = getSprite("assets/reks1"+(i+1)+".png");}}
        if (N1==1){
            Sboom1 = getSprite("assets/dragoboom.png");
            for (int i=0;i<9;i++){
                Sprojectile1 = getSprite("assets/projectile3.png");
                Scharacter1arr[i] = getSprite("assets/drago"+(i+1)+".png");}}
        if (N2==2){
            Sboom2 = getSprite("assets/alienboom.png");
            for (int i=0;i<9;i++){
                Sprojectile2 = getSprite("assets/projectile.png");
                Scharacter2arr[i] = getSprite("assets/ficrus1"+(i+1)+".png");}}
        if (N2==3){
            Sboom2 = getSprite("assets/reksboom.png");
            for (int i=0;i<9;i++){
                Sprojectile2 = getSprite("assets/projectile2.png");
                Scharacter2arr[i] = getSprite("assets/reks"+(i+1)+".png");}}
        if (N2==1){
            Sboom2 = getSprite("assets/dragoboom.png");
            for (int i=0;i<9;i++){
                Sprojectile2 = getSprite("assets/projectile3.png");
                Scharacter2arr[i] = getSprite("assets/drago1"+(i+1)+".png");}}
        Splatform1 = getSprite("assets/platform.png");
        Splatform2 = getSprite("assets/platform.png");
        Splatform3 = getSprite("assets/platform30.png");
        Splatform4 = getSprite("assets/platform150.png");
        Splatform5 = getSprite("assets/platform.png");
        Splatform6 = getSprite("assets/platform.png");
        Sbackground = getSprite("assets/background1.png");
        Sground = getSprite("assets/ground.png");
        Sspike = getSprite("assets/spike.png");
        Sspike2 = getSprite("assets/spike2.png");
        Srightspike = getSprite("assets/rightspike.png");
        Srightspike2 = getSprite("assets/rightspike2.png");
        Supspike = getSprite("assets/upspike.png");
        Supspike2 = getSprite("assets/upspike2.png");
        Splayer1wins = getSprite("assets/player1wins.png");
        Splayer2wins = getSprite("assets/player2wins.png");
        Slifeline1 = getSprite("assets/lifeline.png");
        Slifeline2 = getSprite("assets/lifeline.png");

    }

    //Перерисовка
    private void render() {
        BufferStrategy bs = getBufferStrategy();//Создаётся буфер-стратегия(механизм, с которым можно организовать сложную память на хранение деталей Canvas)
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();//устанавливаем фокус на данном компоненте
            return;
        }
        Graphics g = bs.getDrawGraphics();//Создает графический контекст для буфера
        //Рисование бэкграунда
        Sbackground.draw(g,0, 0, WIDTH, HEIGHT);
        //Рисование персонажа 1
        if (!Player2win) {
            if (time1 > 40) {
                if (N1 == 3) {
                    Scharacter1arr[0].draw(g, character1.getX() - 86, character1.getY() - 80);}
                if (N1 == 2) {
                    Scharacter1arr[0].draw(g, character1.getX() - 65, character1.getY() - 80);}
                if (N1 == 1) {
                    Scharacter1arr[0].draw(g, character1.getX() - 65, character1.getY() - 80);}
            }
            else {
                //Анимация выстрела
                if (N1 == 3) {
                    Scharacter1arr[(int) (time1 / 5)].draw(g, character1.getX() - 86, character1.getY() - 80);}
                if (N1 == 2) {
                    Scharacter1arr[(int) (time1 / 5)].draw(g, character1.getX() - 65, character1.getY() - 80);}
                if (N1 == 1) {
                    Scharacter1arr[(int) (time1 / 5)].draw(g, character1.getX() - 65, character1.getY() - 80);}
            }
        }
        else{
            //Клякса вместо персонажа и надпись о победе
            Splayer1wins.draw(g,WIDTH/2 - 193,HEIGHT/2 - 91,387,183);
            Sboom1.draw(g,character1.getX()-68,character1.getY()-75);}
        //Рисование персонажа 2
        if (!Player1win){
            if (time2>40){
                if (N2==3){
                    Scharacter2arr[0].draw(g, character2.getX()-70, character2.getY()-80);}
                if (N2==1){
                    Scharacter2arr[0].draw(g, character2.getX()-92, character2.getY()-80);}
                if (N2==2){
                    Scharacter2arr[0].draw(g, character2.getX()-61, character2.getY()-80);}
            }
            else{
                if (N2==3){
                    Scharacter2arr[(int)(time2/5)].draw(g, character2.getX()-70, character2.getY()-80);}
                if (N2==1){
                    Scharacter2arr[(int)(time2/5)].draw(g, character2.getX()-92, character2.getY()-80);}
                if (N2==2){
                    Scharacter2arr[(int)(time2/5)].draw(g, character2.getX()-61, character2.getY()-80);}
            }}
        else {
            Sboom2.draw(g, character2.getX()-67, character2.getY()-75);
            Splayer2wins.draw(g,WIDTH/2 - 193,HEIGHT/2 - 91,387,183);
        }
        //Рисование снарядов
        for (int i=0;i<projectiles1.size();i++){
            Sprojectile1.draw(g,projectiles1.get(i).getX()-11,projectiles1.get(i).getY()-10);
        }
        for (int i=0;i<projectiles2.size();i++){
            Sprojectile2.draw(g,projectiles2.get(i).getX()-11,projectiles2.get(i).getY()-10);
        }
        //Рисование платформ
        Splatform1.draw(g,(int)(platform1.getX() - Math.abs((platform1.getWidth()/2)*platform1.getCosalpha())),(int)(platform1.getY()-(platform1.getWidth()/2)*Math.sqrt(1-platform1.getCosalpha()*platform1.getCosalpha())-10),(int)(Math.abs(platform1.getWidth()*platform1.getCosalpha())),(int)(platform1.getWidth()*Math.sqrt(1-platform1.getCosalpha()*platform1.getCosalpha())+24*platform1.getCosalpha()));
        Splatform2.draw(g,(int)(platform2.getX() - Math.abs((platform2.getWidth()/2)*platform2.getCosalpha())),(int)(platform2.getY()-(platform2.getWidth()/2)*Math.sqrt(1-platform2.getCosalpha()*platform2.getCosalpha())-10),(int)(Math.abs(platform2.getWidth()*platform2.getCosalpha())),(int)(platform2.getWidth()*Math.sqrt(1-platform2.getCosalpha()*platform2.getCosalpha())+24*platform1.getCosalpha()));
        Splatform3.draw(g,(int)(platform3.getX() - Math.abs((platform3.getWidth()/2)*platform3.getCosalpha())),(int)(platform3.getY()-(platform3.getWidth()/2)*Math.sqrt(1-platform3.getCosalpha()*platform3.getCosalpha())-10),(int)(Math.abs(platform3.getWidth()*platform3.getCosalpha())),(int)(platform3.getWidth()*Math.sqrt(1-platform3.getCosalpha()*platform3.getCosalpha())+24*platform1.getCosalpha()));
        Splatform4.draw(g,(int)(platform4.getX() - Math.abs((platform4.getWidth()/2)*platform4.getCosalpha())),(int)(platform4.getY()-(platform4.getWidth()/2)*Math.sqrt(1-platform4.getCosalpha()*platform4.getCosalpha())-10),(int)(Math.abs(platform4.getWidth()*platform4.getCosalpha())),(int)(platform4.getWidth()*Math.sqrt(1-platform4.getCosalpha()*platform4.getCosalpha())+24*platform1.getCosalpha()));
        Splatform5.draw(g,(int)(platform5.getX() - Math.abs((platform5.getWidth()/2)*platform5.getCosalpha())),(int)(platform5.getY()-(platform5.getWidth()/2)*Math.sqrt(1-platform5.getCosalpha()*platform5.getCosalpha())-10),(int)(Math.abs(platform5.getWidth()*platform5.getCosalpha())),(int)(platform5.getWidth()*Math.sqrt(1-platform5.getCosalpha()*platform5.getCosalpha())+24*platform1.getCosalpha()));
        Splatform6.draw(g,(int)(platform6.getX() - Math.abs((platform6.getWidth()/2)*platform6.getCosalpha())),(int)(platform6.getY()-(platform6.getWidth()/2)*Math.sqrt(1-platform6.getCosalpha()*platform6.getCosalpha())-10),(int)(Math.abs(platform6.getWidth()*platform6.getCosalpha())),(int)(platform6.getWidth()*Math.sqrt(1-platform6.getCosalpha()*platform6.getCosalpha())+24*platform1.getCosalpha()));

        //Рисование шипов
        for (int i=0;i<15;i++){
            Sspike.draw(g,arr[i].getX(),arr[i].getY());
        }
        for (int i=15;i<30;i++){
            Sspike2.draw(g,arr[i].getX(),arr[i].getY());
        }
        for (int i=30;i<45;i++){
            Srightspike.draw(g,arr[i].getX(),arr[i].getY());
        }
        for (int i=45;i<60;i++){
            Srightspike2.draw(g,arr[i].getX(),arr[i].getY());
        }
        for (int i=60;i<111;i+=2){
            Supspike.draw(g,arr[i].getX(),arr[i].getY());
        }
        for (int i=61;i<111;i+=2){
            Supspike2.draw(g,arr[i].getX(),arr[i].getY());
        }
        //Рисование земли
        Sground.draw(g,0, 0,WIDTH,HEIGHT);
        //Рисование шкалы жизней
        if (character2.getLife()>=0){
            Slifeline1.draw(g, (int) (0.022 * WIDTH), (int) (0.038 * HEIGHT), character2.getLife(), (int) (0.0208333 * HEIGHT));}
        if (character1.getLife()>=0){
            Slifeline2.draw(g, WIDTH - (int) (0.018 * WIDTH) - character1.getLife(), (int) (0.038 * HEIGHT), character1.getLife(), (int) (0.0208333 * HEIGHT));
        }
        //Рисование количества патронов


        g.setColor(Color.WHITE);
        String s1 = "" +character2.getCnt();
        String s2 = ""+character1.getCnt();
        g.drawString(s1, WIDTH/10, (int) (0.038 * HEIGHT) + (int) (0.0208333 * HEIGHT)-5 );
        g.drawString(s2, WIDTH*9/10, (int) (0.038 * HEIGHT)+(int) (0.0208333 * HEIGHT)-5);
        //После того, как нарисовали, освобождаем все используемые системные ресурсы
        g.dispose();
        //Делаем буфер видимым
        bs.show();
    }

    private void update(long delta)
    {

        if (character1.getLife()<0){
            Player2win = true;
        }
        if (character2.getLife()<0){
            Player1win = true;
        }
        time1+=1;
        time2+=1;
        //Движение в поле тяжести Земли
        if (!Player2win&&!Player1win) {
            for (int i=0;i<projectiles2.size();i++){
                projectiles2.get(i).setM(0.5);
                projectiles2.get(i).setR(10);
                projectiles2.get(i).setAy(0.001);
                projectiles2.get(i).setVy(projectiles2.get(i).getVy() + projectiles2.get(i).getAy() * delta);
                if ((projectiles2.get(i).getX()<0)||(projectiles2.get(i).getX()>1920)||(projectiles2.get(i).getY()<0)||(projectiles2.get(i).getY()>1080)){
                   projectiles2.remove(i);
                }
            }
            for (int i=0;i<projectiles1.size();i++){
                projectiles1.get(i).setM(0.5);
                projectiles1.get(i).setR(10);
                projectiles1.get(i).setAy(0.001);
                projectiles1.get(i).setVy(projectiles1.get(i).getVy() + projectiles1.get(i).getAy() * delta);
                if ((projectiles1.get(i).getX()<0)||(projectiles1.get(i).getX()>1920)||(projectiles1.get(i).getY()<0)||(projectiles1.get(i).getY()>1080)){
                    projectiles1.remove(i);
                }
            }
            character2.setAy(0.001);
            character2.setVy(character2.getVy() + character2.getAy() * delta);
            character1.setAy(0.001);
            character1.setVy(character1.getVy() + character1.getAy() * delta);


            //Взаимодействие с шипами
            for (int i = 0; i < 111; i++) {
                if (setVzaimodeistviewithSpike(character1, arr[i])) {
                    Player2win = true;
                }
                if (setVzaimodeistviewithSpike(character2, arr[i])) {
                    Player1win = true;
                }
            }


            //Физика первого персонажа
            if (rightPressed) {
                character1.setAx(0.0005);
            }
            if (leftPressed) {
                character1.setAx(-0.0005);
            }
            if (!rightPressed && !leftPressed) {
                if (Math.abs(character1.getVx()) < 0.03) {
                    character1.setAx(0);
                } else {
                    if (character1.getVx() > 0) {
                        character1.setAx(character1.getAx() - 0.00001);
                    }
                    if (character1.getVx() < 0) {
                        character1.setAx(character1.getAx() + 0.00001);
                    }

                }
            }

            //Физика второго персонажа
            if (dPressed) {
                character2.setAx(0.0005);
            }
            if (aPressed) {
                character2.setAx(-0.0005);
            }
            if (!aPressed && !dPressed) {
                if (Math.abs(character2.getVx()) < 0.03) {
                    character2.setAx(0);
                } else {
                    if (character2.getVx() > 0) {
                        character2.setAx(character2.getAx() - 0.00001);
                    }
                    if (character2.getVx() < 0) {
                        character2.setAx(character2.getAx() + 0.00001);
                    }

                }
            }




            //Взаимодействие двух персонажей
            setVzaimodeistviewthCharacter(character1, character2);


            //Взаимодействие персонажей и платформ
            setVzaimodeistvie(character1, platform1);
            setVzaimodeistvie(character2, platform1);
            setVzaimodeistvie(character1, platform2);
            setVzaimodeistvie(character2, platform2);
            setVzaimodeistvie(character1, platform3);
            setVzaimodeistvie(character2, platform3);
            setVzaimodeistvie(character1, platform4);
            setVzaimodeistvie(character2, platform4);
            setVzaimodeistvie(character1, platform5);
            setVzaimodeistvie(character2, platform5);
            setVzaimodeistvie(character1, platform6);
            setVzaimodeistvie(character2, platform6);


            //Прыжок(если есть точка соприкосновения)
            if (upPressed) {
                boolean b = true;
                if (tochka(platform1, character1)) {
                    character1.setVy(character1.getVy() - 0.5);
                    b = false;
                    character1.setY(character1.getY() - 5);
                }
                if (tochka(platform2, character1) && b) {
                    character1.setVy(character1.getVy() - 0.5);
                    b = false;
                    character1.setY(character1.getY() - 5);
                }
                if (tochka(platform3, character1) && b) {
                    character1.setVy(character1.getVy() - 0.5);
                    b = false;
                    character1.setY(character1.getY() - 5);
                }
                if (tochka(platform4, character1) && b) {
                    character1.setVy(character1.getVy() - 0.5);
                    b = false;
                    character1.setY(character1.getY() - 5);
                }
                if (tochka(platform5, character1) && b) {
                    character1.setVy(character1.getVy() - 0.5);
                    b = false;
                    character1.setY(character1.getY() - 5);
                }
                if (tochka(platform6, character1) && b) {
                    character1.setVy(character1.getVy() - 0.5);
                    character1.setY(character1.getY() - 5);
                }
            }
            if (wPressed) {
                boolean b = true;
                if (tochka(platform1, character2) && b) {
                    character2.setVy(character2.getVy() - 0.5);
                    b = false;
                    character2.setY(character2.getY() - 5);
                }
                if (tochka(platform2, character2) && b) {
                    character2.setVy(character2.getVy() - 0.5);
                    b = false;
                    character2.setY(character2.getY() - 5);
                }
                if (tochka(platform3, character2) && b) {
                    character2.setVy(character2.getVy() - 0.5);
                    b = false;
                    character2.setY(character2.getY() - 5);
                }
                if (tochka(platform4, character2) && b) {
                    character2.setVy(character2.getVy() - 0.5);
                    b = false;
                    character2.setY(character2.getY() - 5);
                }
                if (tochka(platform5, character2) && b) {
                    character2.setVy(character2.getVy() - 0.5);
                    b = false;
                    character2.setY(character2.getY() - 5);
                }
                if (tochka(platform6, character2) && b) {
                    character2.setVy(character2.getVy() - 0.5);
                    character2.setY(character2.getY() - 5);
                }

            }

            //Снаряд
            if (fPressed && (character2.getCnt() > 0) &&(time2 > 30)) {
                Projectile projectile2 = new Projectile();
                projectile2.setY(character2.getY() - 35);
                projectile2.setX(character2.getX() + (int) character2.getR() + 20);
                projectile2.setVx(character2.getVx() + 1.2);
                projectile2.setVy(character2.getVy());
                projectile2.setAx(0);
                projectiles2.add(projectile2);
                character2.reduceByOne();
                character2.setVx(character2.getVx() - 0.1);
                time2 = 0;
            }


            if (ctrlPressed && (character1.getCnt() > 0) && (time1 > 30)) {
                Projectile projectile1 = new Projectile();
                projectile1.setY(character1.getY() - 35);
                projectile1.setX(character1.getX() - (int) character1.getR() - 20);
                projectile1.setVx(character1.getVx() - 1.2);
                projectile1.setVy(character1.getVy());
                projectile1.setAx(0);
                projectiles1.add(projectile1);
                character1.reduceByOne();
                character1.setVx(character1.getVx() + 0.1);
                time1 = 0;
            }

            //Взаимодействие персонажей и пуль
            for (int i=0;i<projectiles1.size();i++){
                setVzaimodeistviewithProjectile(character1, projectiles1.get(i));
                setVzaimodeistviewithProjectile(character2, projectiles1.get(i));
            }
            for (int i=0;i<projectiles2.size();i++) {
                setVzaimodeistviewithProjectile(character1, projectiles2.get(i));
                setVzaimodeistviewithProjectile(character2, projectiles2.get(i));
            }

            //Взаимодействие пуль и платформ
            for (int i=0;i<projectiles1.size();i++){
                setVzaimodeistviePlatformwithProjectile(projectiles1.get(i), platform1);
                setVzaimodeistviePlatformwithProjectile(projectiles1.get(i), platform2);
                setVzaimodeistviePlatformwithProjectile(projectiles1.get(i), platform3);
                setVzaimodeistviePlatformwithProjectile(projectiles1.get(i), platform4);
                setVzaimodeistviePlatformwithProjectile(projectiles1.get(i), platform5);
                setVzaimodeistviePlatformwithProjectile(projectiles1.get(i), platform6);
            }
            for (int i=0;i<projectiles2.size();i++){
                setVzaimodeistviePlatformwithProjectile(projectiles2.get(i), platform1);
                setVzaimodeistviePlatformwithProjectile(projectiles2.get(i), platform2);
                setVzaimodeistviePlatformwithProjectile(projectiles2.get(i), platform3);
                setVzaimodeistviePlatformwithProjectile(projectiles2.get(i), platform4);
                setVzaimodeistviePlatformwithProjectile(projectiles2.get(i), platform5);
                setVzaimodeistviePlatformwithProjectile(projectiles2.get(i), platform6);
            }

            //Взаимодействие пуль между собой
            for (int i=0;i<projectiles1.size();i++){
                for (int j=0;j<projectiles2.size();j++){
                    setVzaimodeistvieProjectilewithProjectile(projectiles1.get(i),projectiles2.get(j));
                }
            }
            for (int i=0;i<projectiles1.size();i++){
                for (int j=0;j<projectiles1.size();j++){
                    if (i!=j){
                    setVzaimodeistvieProjectilewithProjectile(projectiles1.get(i),projectiles1.get(j));}
                }
            }
            for (int i=0;i<projectiles2.size();i++){
                for (int j=0;j<projectiles2.size();j++){
                    if (i!=j){
                        setVzaimodeistvieProjectilewithProjectile(projectiles2.get(i),projectiles2.get(j));}
                }
            }


            //Изменение координат(Равноускоренное движение)
            character2.setX((int) (Math.round(character2.getX() + character2.getVx() * delta + character2.getAx() * delta * delta / 2)));
            character2.setVx(character2.getVx() + character2.getAx() * delta);

            character1.setX((int) (Math.round(character1.getX() + character1.getVx() * delta + character1.getAx() * delta * delta / 2)));
            character1.setVx(character1.getVx() + character1.getAx() * delta);

            platform1.setX((int) (Math.round(platform1.getX() + platform1.getVx() * delta + platform1.getAx() * delta * delta / 2)));
            platform1.setY((int) (Math.round(platform1.getY() + platform1.getVy() * delta + platform1.getAy() * delta * delta / 2)));
            platform2.setX((int) (Math.round(platform2.getX() + platform2.getVx() * delta + platform2.getAx() * delta * delta / 2)));
            platform2.setY((int) (Math.round(platform2.getY() + platform2.getVy() * delta + platform2.getAy() * delta * delta / 2)));
            platform3.setX((int) (Math.round(platform3.getX() + platform3.getVx() * delta + platform3.getAx() * delta * delta / 2)));
            platform3.setY((int) (Math.round(platform3.getY() + platform3.getVy() * delta + platform3.getAy() * delta * delta / 2)));
            platform4.setX((int) (Math.round(platform4.getX() + platform4.getVx() * delta + platform4.getAx() * delta * delta / 2)));
            platform4.setY((int) (Math.round(platform4.getY() + platform4.getVy() * delta + platform4.getAy() * delta * delta / 2)));
            platform5.setX((int) (Math.round(platform5.getX() + platform5.getVx() * delta + platform5.getAx() * delta * delta / 2)));
            platform5.setY((int) (Math.round(platform5.getY() + platform5.getVy() * delta + platform5.getAy() * delta * delta / 2)));
            platform6.setX((int) (Math.round(platform6.getX() + platform6.getVx() * delta + platform6.getAx() * delta * delta / 2)));
            platform6.setY((int) (Math.round(platform6.getY() + platform6.getVy() * delta + platform6.getAy() * delta * delta / 2)));

            character2.setY((int) (Math.round(character2.getY() + character2.getVy() * delta + character2.getAy() * delta * delta / 2)));
            character1.setY((int) (Math.round(character1.getY() + character1.getVy() * delta + character1.getAy() * delta * delta / 2)));

            for(int i=0;i<projectiles1.size();i++){
                projectiles1.get(i).setX((int) (projectiles1.get(i).getX() + projectiles1.get(i).getVx() * delta + projectiles1.get(i).getAx() * delta * delta / 2));
                projectiles1.get(i).setVx(projectiles1.get(i).getVx() + projectiles1.get(i).getAx() * delta);
                projectiles1.get(i).setY((int) (projectiles1.get(i).getY() + projectiles1.get(i).getVy() * delta + projectiles1.get(i).getAy() * delta * delta / 2));
            }
            for(int i=0;i<projectiles2.size();i++){
                projectiles2.get(i).setX((int) (projectiles2.get(i).getX() + projectiles2.get(i).getVx() * delta + projectiles2.get(i).getAx() * delta * delta / 2));
                projectiles2.get(i).setVx(projectiles2.get(i).getVx() + projectiles2.get(i).getAx() * delta);
                projectiles2.get(i).setY((int) (projectiles2.get(i).getY() + projectiles2.get(i).getVy() * delta + projectiles2.get(i).getAy() * delta * delta / 2));
            }
        }

    }

    //метод, описывающие взаимодействие персонажей между собой
    private void setVzaimodeistviewthCharacter(Character character1,Character character2){
        double R1 = character1.getR();
        double R2 = character2.getR();
        double x1 = character1.getX();
        double x2 = character2.getX();
        double y1 = character1.getY();
        double y2 = character2.getY();
        double v1x = character1.getVx();
        double v2x = character2.getVx();
        double v1y = character1.getVy();
        double v2y = character2.getVy();
        double m1 = character1.getM();
        double m2 = character2.getM();
        if (R1+R2>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))) {
            double cosa = Math.abs(x2 - x1) / Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            double sina = Math.sqrt(1 - cosa * cosa);
            //проекция на оси OV и OW
            double v1v;
            double v2v;
            double v1w;
            double v2w;
            if ((x1 < x2) && (y1 > y2) || (x1 > x2) && (y1 < y2)) {
                v1v = v1x * cosa - v1y * sina;
                v2v = v2x * cosa - v2y * sina;
                v1w = v1x * sina + v1y * cosa;
                v2w = v2x * sina + v2y * cosa;
            } else {
                v1v = v1x * cosa + v1y * sina;
                v2v = v2x * cosa + v2y * sina;
                v1w = -v1x * sina + v1y * cosa;
                v2w = -v2x * sina + v2y * cosa;
            }
            //Скорость изменяется только по оси OV
            double u2 = (2 * m1 * v1v - m1 * v2v + m2 * v2v) / (m1 + m2);
            double u1 = (2 * m2 * v2v - m2 * v1v + m1 * v1v) / (m1 + m2);
            //Проекция обратно на оси OX и OY
            if ((x1 < x2) && (y1 > y2) || (x1 > x2) && (y1 < y2)) {
                character1.setVx(u1 * cosa + v1w * sina);
                character2.setVx(u2 * cosa + v2w * sina);
                character1.setVy(-u1 * sina + v1w * cosa);
                character2.setVy(-u2 * sina + v2w * cosa);
            } else {
                character1.setVx(u1 * cosa - v1w * sina);
                character2.setVx(u2 * cosa - v2w * sina);
                character1.setVy(u1 * sina + v1w * cosa);
                character2.setVy(u2 * sina + v2w * cosa);
            }
            if (x1 < x2) {
                character1.setX((int) (Math.round(x2 - (R1 + R2) * cosa)));
                character2.setX((int) (Math.round(x1 + (R1 + R2) * cosa)));
            }
            else{
                character1.setX((int) (Math.round(x2 + (R1 + R2) * cosa)));
                character2.setX((int) (Math.round(x1 - (R1 + R2) * cosa)));
            }
            if (y1 < y2) {
                character1.setY((int)(Math.round(y2 - (R1+R2)*sina)));
                character2.setY((int)(Math.round(y1 + (R1+R2)*sina)));
            }
            else{
                character1.setY((int)(Math.round(y2 + (R1+R2)*sina)));
                character2.setY((int)(Math.round(y1 - (R1+R2)*sina)));
            }
        }
    }
    //метод, описывающие взаимодействие снаряда и персонажа
    private void setVzaimodeistviewithProjectile(Character character,Projectile projectile){
        double R1 = character.getR();
        double R2 = projectile.getR();
        double x1 = character.getX();
        double x2 = projectile.getX();
        double y1 = character.getY();
        double y2 = projectile.getY();
        double v1x = character.getVx();
        double v2x = projectile.getVx();
        double v1y = character.getVy();
        double v2y = projectile.getVy();
        double m1 = character.getM();
        double m2 = projectile.getM();
        if (R1+R2>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))) {
            double cosa = Math.abs(x2 - x1) / Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            double sina = Math.sqrt(1 - cosa * cosa);
            //проекция на оси OV и OW
            double v1v;
            double v2v;
            double v1w;
            double v2w;
            if ((x1 < x2) && (y1 > y2) || (x1 > x2) && (y1 < y2)) {
                v1v = v1x * cosa - v1y * sina;
                v2v = v2x * cosa - v2y * sina;
                v1w = v1x * sina + v1y * cosa;
                v2w = v2x * sina + v2y * cosa;
            } else {
                v1v = v1x * cosa + v1y * sina;
                v2v = v2x * cosa + v2y * sina;
                v1w = -v1x * sina + v1y * cosa;
                v2w = -v2x * sina + v2y * cosa;
            }
            //Скорость изменяется только по оси OV
            double u2 = (2 * m1 * v1v - m1 * v2v + m2 * v2v) / (m1 + m2);
            double u1 = (2 * m2 * v2v - m2 * v1v + m1 * v1v) / (m1 + m2);
            //Проекция обратно на оси OX и OY
            if ((x1 < x2) && (y1 > y2) || (x1 > x2) && (y1 < y2)) {
                character.setVx(u1 * cosa + v1w * sina);
                projectile.setVx(u2 * cosa + v2w * sina);
                character.setVy(-u1 * sina + v1w * cosa);
                projectile.setVy(-u2 * sina + v2w * cosa);
            } else {
                character.setVx(u1 * cosa - v1w * sina);
                projectile.setVx(u2 * cosa - v2w * sina);
                character.setVy(u1 * sina + v1w * cosa);
                projectile.setVy(u2 * sina + v2w * cosa);
            }
            if (x1 < x2) {
                character.setX((int) (Math.round(x2 - (R1 + R2) * cosa)));
                projectile.setX((int) (Math.round(x1 + (R1 + R2) * cosa)));
            }
            else{
                character.setX((int) (Math.round(x2 + (R1 + R2) * cosa)));
                projectile.setX((int) (Math.round(x1 - (R1 + R2) * cosa)));
            }
            if (y1 < y2) {
                character.setY((int)(Math.round(y2 - (R1+R2)*sina)));
                projectile.setY((int)(Math.round(y1 + (R1+R2)*sina)));
            }
            else{
                character.setY((int)(Math.round(y2 + (R1+R2)*sina)));
                projectile.setY((int)(Math.round(y1 - (R1+R2)*sina)));
            }
            if (projectile.getK()==0){
            character.setLife((int)(character.getLife() - (0.4399707*WIDTH)/10));//кол-во жизней уменьшается на 1/10 всех жизней
            projectile.setK(1);
            }
        }
    }
    //метод, описывающие взаимодействие снарядов между собой
    private void setVzaimodeistvieProjectilewithProjectile(Projectile projectile1,Projectile projectile2){
        double R1 = projectile1.getR();
        double R2 = projectile2.getR();
        double x1 = projectile1.getX();
        double x2 = projectile2.getX();
        double y1 = projectile1.getY();
        double y2 = projectile2.getY();
        double v1x = projectile1.getVx();
        double v2x = projectile2.getVx();
        double v1y = projectile1.getVy();
        double v2y = projectile2.getVy();
        double m1 = projectile1.getM();
        double m2 = projectile2.getM();
        if (R1+R2>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))) {
            double cosa = Math.abs(x2 - x1) / Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            double sina = Math.sqrt(1 - cosa * cosa);
            //проекция на оси OV и OW
            double v1v;
            double v2v;
            double v1w;
            double v2w;
            if ((x1 < x2) && (y1 > y2) || (x1 > x2) && (y1 < y2)) {
                v1v = v1x * cosa - v1y * sina;
                v2v = v2x * cosa - v2y * sina;
                v1w = v1x * sina + v1y * cosa;
                v2w = v2x * sina + v2y * cosa;
            } else {
                v1v = v1x * cosa + v1y * sina;
                v2v = v2x * cosa + v2y * sina;
                v1w = -v1x * sina + v1y * cosa;
                v2w = -v2x * sina + v2y * cosa;
            }
            //Скорость изменяется только по оси OV
            double u2 = (2 * m1 * v1v - m1 * v2v + m2 * v2v) / (m1 + m2);
            double u1 = (2 * m2 * v2v - m2 * v1v + m1 * v1v) / (m1 + m2);
            //Проекция обратно на оси OX и OY
            if ((x1 < x2) && (y1 > y2) || (x1 > x2) && (y1 < y2)) {
                projectile1.setVx(u1 * cosa + v1w * sina);
                projectile2.setVx(u2 * cosa + v2w * sina);
                projectile1.setVy(-u1 * sina + v1w * cosa);
                projectile2.setVy(-u2 * sina + v2w * cosa);
            } else {
                projectile1.setVx(u1 * cosa - v1w * sina);
                projectile2.setVx(u2 * cosa - v2w * sina);
                projectile1.setVy(u1 * sina + v1w * cosa);
                projectile2.setVy(u2 * sina + v2w * cosa);
            }
            if (x1 < x2) {
                projectile1.setX((int) (Math.round(x2 - (R1 + R2) * cosa)));
                projectile2.setX((int) (Math.round(x1 + (R1 + R2) * cosa)));
            }
            else{
                projectile1.setX((int) (Math.round(x2 + (R1 + R2) * cosa)));
                projectile2.setX((int) (Math.round(x1 - (R1 + R2) * cosa)));
            }
            if (y1 < y2) {
                projectile1.setY((int)(Math.round(y2 - (R1+R2)*sina)));
                projectile2.setY((int)(Math.round(y1 + (R1+R2)*sina)));
            }
            else{
                projectile1.setY((int)(Math.round(y2 + (R1+R2)*sina)));
                projectile2.setY((int)(Math.round(y1 - (R1+R2)*sina)));
            }
        }
    }
    //метод, описывающие взаимодействие персонажа и платформы
    private void setVzaimodeistvie(Character character,Platform platform){

        double x1 = character.getX();
        double x2 = platform.getX();
        double y1 = character.getY();
        double y2 = platform.getY();
        double R1 = character.getR();
        double v1x= character.getVx();
        double v1y = character.getVy();
        double width1 = platform.getWidth();
        double v2x = platform.getVx();
        double v2y = platform.getVy();
        double m1 = character.getM();
        double m2 = platform.getM();
        double cosalpha = platform.getCosalpha();//угол наклона платформы.
        double sinalpha = Math.sqrt(1 - cosalpha*cosalpha);
          double AB = width1;
          double x3 = x2+(width1*(cosalpha))/2;
          double y3 = y2-(width1*sinalpha)/2;
          double x4 = x2-(width1*(cosalpha))/2;
          double y4 = y2+(width1*sinalpha)/2;
          double AO =Math.sqrt((x1 - x3)*(x1 - x3)+(y1 -y3 )*(y1 -y3 ));
          double BO =Math.sqrt((x1 - x4)*(x1 - x4)+(y1 -y4 )*(y1 -y4 ));
          double p =(AO+AB+BO)/2;
          double S = Math.sqrt(p*(p-AB)*(p-AO)*(p-BO));
          double OH = 2*S/AB;
          double a = (y4 - y3)/(x4 - x3);
          double b = y3 - a*x3;
          double yn =  a*x1 + b;//координата точки на платформе, находящейся под(над) персонажем
         if ((width1/2)>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))){
            if (R1>=OH){
                  if (cosalpha>0){
                      double v1w = v1x * cosalpha - v1y * sinalpha;
                      double v2w = v2x * cosalpha - v2y * sinalpha;
                      double v1v = -v1x * sinalpha - v1y * cosalpha;
                      double v2v = -v2x * sinalpha - v2y * cosalpha;
                      double u = (m1 * v1v + m2 * v2v) / (m1 + m2);
                      character.setVx(-u*sinalpha +v1w * cosalpha);
                      platform.setVx(-u*sinalpha +v2w * cosalpha);
                      character.setVy(-u * cosalpha -v1w * sinalpha);
                      platform.setVy(-u * cosalpha-v2w * sinalpha);
                      character.setY((int)(Math.round(yn-R1+1)));}
                  else{
                      double v1w = v1x * cosalpha - v1y * sinalpha;
                      double v2w = v2x * cosalpha - v2y * sinalpha;
                      double v1v = v1x * sinalpha + v1y * cosalpha;
                      double v2v = v2x * sinalpha + v2y * cosalpha;
                      double u = (m1 * v1v + m2 * v2v) / (m1 + m2);
                      character.setVx(u*sinalpha +v1w * cosalpha);
                      platform.setVx(u*sinalpha +v2w * cosalpha);
                      character.setVy(u * cosalpha -v1w * sinalpha);
                      platform.setVy(u * cosalpha-v2w * sinalpha);
                      character.setY((int)(Math.round(yn-R1+1)));
                  }
            }
         }
    }

    //метод, описывающие взаимодействие снаряда и платформы
    private void setVzaimodeistviePlatformwithProjectile(Projectile projectile,Platform platform){

        double x1 = projectile.getX();
        double x2 = platform.getX();
        double y1 = projectile.getY();
        double y2 = platform.getY();
        double R1 = projectile.getR();
        double v1x= projectile.getVx();
        double v1y = projectile.getVy();
        double width1 = platform.getWidth();
        double v2x = platform.getVx();
        double v2y = platform.getVy();
        double m1 = projectile.getM();
        double m2 = platform.getM();
        double cosalpha = platform.getCosalpha();//угол наклона платформы.
        double sinalpha = Math.sqrt(1 - cosalpha*cosalpha);
        double AB = width1;
        double x3 = x2+(width1*(cosalpha))/2;
        double y3 = y2-(width1*sinalpha)/2;
        double x4 = x2-(width1*(cosalpha))/2;
        double y4 = y2+(width1*sinalpha)/2;
        double AO =Math.sqrt((x1 - x3)*(x1 - x3)+(y1 -y3 )*(y1 -y3 ));
        double BO =Math.sqrt((x1 - x4)*(x1 - x4)+(y1 -y4 )*(y1 -y4 ));
        double p =(AO+AB+BO)/2;
        double S = Math.sqrt(p*(p-AB)*(p-AO)*(p-BO));
        double OH = 2*S/AB;
        double a = (y4 - y3)/(x4 - x3);
        double b = y3 - a*x3;
        double yn =  a*x1 + b;//координата точки на платформе, находящейся под(над) персонажем
        if ((width1/2)>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))){
            if (R1>=OH){
                    if (cosalpha>0){
                        double v1w = v1x * cosalpha - v1y * sinalpha;
                        double v2w = v2x * cosalpha - v2y * sinalpha;
                        double v1v = -v1x * sinalpha - v1y * cosalpha;
                        double v2v = -v2x * sinalpha - v2y * cosalpha;
                        double u = (m1 * v1v + m2 * v2v) / (m1 + m2);
                        projectile.setVx(-u*sinalpha +v1w * cosalpha);
                        platform.setVx(-u*sinalpha +v2w * cosalpha);
                        projectile.setVy(-u * cosalpha -v1w * sinalpha);
                        platform.setVy(-u * cosalpha-v2w * sinalpha);
                        projectile.setY((int)(Math.round(yn-R1+1)));}
                    else{
                        double v1w = v1x * cosalpha - v1y * sinalpha;
                        double v2w = v2x * cosalpha - v2y * sinalpha;
                        double v1v = v1x * sinalpha + v1y * cosalpha;
                        double v2v = v2x * sinalpha + v2y * cosalpha;
                        double u = (m1 * v1v + m2 * v2v) / (m1 + m2);
                        projectile.setVx(u*sinalpha +v1w * cosalpha);
                        platform.setVx(u*sinalpha +v2w * cosalpha);
                        projectile.setVy(u * cosalpha -v1w * sinalpha);
                        platform.setVy(u * cosalpha-v2w * sinalpha);
                        projectile.setY((int)(Math.round(yn-R1+1)));
                    }


            }}
    }
    //метод, проверяющий, наткнулся ли персонаж на шип
    private boolean setVzaimodeistviewithSpike(Character character,Spike spike){
        boolean b = false;
        double x1 = character.getX();
        double y1 = character.getY();
        double x2 = spike.getXs();
        double y2 = spike.getYs();
        double R1 = character.getR();
        if (R1-2>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))){
            b = true;
        }
        return b;
    }

    //метод, проверяющий, есть ли у персонажа точка опоры
    private boolean tochka(Platform platform,Character character){
        boolean c=false;
        double x1 = character.getX();
        double x2 = platform.getX();
        double y1 = character.getY();
        double y2 = platform.getY();
        double R1 = character.getR();
        double width1 = platform.getWidth();
        double cosalpha = platform.getCosalpha();//угол наклона платформы.
        double sinalpha = Math.sqrt(1 - cosalpha*cosalpha);
        if ((width1/2)>=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))){
            double AB = width1;
            double AO =Math.sqrt((x1 - x2-(width1*cosalpha)/2)*(x1 - x2-(width1*cosalpha)/2)+(y1 -y2+(width1*sinalpha)/2 )*(y1 -y2+(width1*sinalpha)/2 ));
            double BO =Math.sqrt((x1 - x2+(width1*cosalpha)/2)*(x1 - x2+(width1*cosalpha)/2)+(y1 -y2-(width1*sinalpha)/2 )*(y1 -y2-(width1*sinalpha)/2 ));
            double p =(AO+AB+BO)/2;
            double S = Math.sqrt(p*(p-AB)*(p-AO)*(p-BO));
            double OH = 2*S/AB;
                double x3 = x2+(width1*(cosalpha))/2;
                double y3 = y2-(width1*sinalpha)/2;
                double x4 = x2-(width1*(cosalpha))/2;
                double y4 = y2+(width1*sinalpha)/2;
                double a = (y4 - y3)/(x4 - x3);
                double b = y3 - a*x3;
                double yn =  a*x1 + b;//координата точки на платформе, находящейся под(над) персонажем
            if (R1>=OH) {
                if (yn >= y1){
                  c = true;
                }
            }}
            return c;
    }

    //метод, возвращающий Sprite картинки по её названию(String)
    private Sprite getSprite(String path) {
            URL url = Game.class.getResource(path);
        Image sourceImage = new ImageIcon(url).getImage();
        return new Sprite(sourceImage);
    }

    //Слушатель клавиатуры
    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_F) {
                fPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                ctrlPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }


        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_F) {
                fPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                ctrlPressed = false;
            }
        }
    }
}
