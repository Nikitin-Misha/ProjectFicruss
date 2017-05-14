package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Main {
    //Получение размеров экрана
    private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private static int WIDTH = dim.width;
    private static int HEIGHT = dim.height;

    private static JFrame frame1=new JFrame();//создаём фрейм

    public static void main(String[] args) {
        CreateGUI();//Создание графического интерфейса пользователя
    }


    private static void CreateGUI(){
        //Устанавливаем размеры фрейма
        frame1.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        //Создание панели "Ficruss", при нажатии на которую начинается игра
        //Для каждой панели хранится 3 изображения, одно из которых рисуется в зависимости от значения индикатора k
        Panel ficruss = new Panel(0,0,457,210,new PanelImage("assets/play1.png","assets/play2.png","assets/play2.png"));
        //Задаются координаты и размеры кнопки
        ficruss.setBounds(WIDTH/2-229,HEIGHT/3-50, ficruss.width, ficruss.height);
        //Создание бэкграунда для 1й сцены
        Panel background1 = new Panel(0,0,WIDTH,HEIGHT,new PanelImage("assets/startframe.png","assets/startframe.png","assets/startframe.png"));
        background1.setBounds(0,0, background1.width, background1.height);
        //Добавление панелей на фрейм
        frame1.add(ficruss);
        frame1.add(background1);
        //установка менеджера компоновки(т.к. устанавливаем координаты панелей вручную, то используем null)
        frame1.setLayout(null);
        //Упаковка - установка правильного размера фрейма, чтобы он вместил всё необходимое
        frame1.pack();
        //Фрейм закрывается по нажатию на крестик(указывается действие, которое необходимо выполнить, когда пользователь нажал на крестик)
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Делает фрейм видимым
        frame1.setVisible(true);

        //Добавление панели "Ficruss" слушателя мыши
        ficruss.addMouseListener(new MouseAdapter() {
            //Метод, вызываемый, когда мышь находится над панелью
            @Override
            public void mouseEntered(MouseEvent e) {
                ficruss.k = -1;//переводим индикатор в состояние -1
                frame1.repaint();//перерисовываем фрейм
            }
            //Метод, вызываемый, когда мышь покинула границы панели
            @Override
            public void mouseExited(MouseEvent e) {
                ficruss.k = 0;//переводим индикатор в состояние 0
                frame1.repaint();//перерисовываем фрейм

            }
            //Метод, обрабатывающий нажатие мышью на кнопку
            @Override
            public void mouseClicked(MouseEvent e) {
                ficruss.k = 1;//переводим индикатор в состояние 1
                //Убираем с фрейма все панели
                frame1.remove(ficruss);
                frame1.remove(background1);
                startClicked();

            }

        });

    }

    private static void startClicked(){
        //Создаем новые панели и добавляем их на фрейм
        //Левая панель выбора персонажа
        Panel panel2 = new Panel(0,0,298,500,new PanelImage("assets/alieninincubator1.png","assets/reksinincubator1.png","assets/dragoinincubator1.png"));
        panel2.setBounds(WIDTH/4-149,HEIGHT/2-250, panel2.width, panel2.height);
        panel2.k=0;
        frame1.add(panel2);

        //Правая панель выбора персонажа
        Panel panel4 = new Panel(0,0,298,500,new PanelImage("assets/alieninincubator.png","assets/reksinincubator.png","assets/dragoinincubator.png"));
        panel4.setBounds(WIDTH/4*3-149,HEIGHT/2-250, panel4.width, panel4.height);
        panel4.k =0;
        frame1.add(panel4);

        //Стрелки выбора персонажа
        Panel rightnarrow1= new Panel(0,0,60,71,new PanelImage("assets/rightnarrow.png","assets/rightnarrow2.png","assets/rightnarrow3.png"));
        rightnarrow1.setBounds(WIDTH/4+150,HEIGHT/2-36,rightnarrow1.width,rightnarrow1.height);
        frame1.add(rightnarrow1);

        Panel rightnarrow2= new Panel(0,0,60,71,new PanelImage("assets/rightnarrow.png","assets/rightnarrow2.png","assets/rightnarrow3.png"));
        rightnarrow2.setBounds(WIDTH/4*3+150,HEIGHT/2-36,rightnarrow2.width,rightnarrow2.height);
        frame1.add(rightnarrow2);

        Panel leftnarrow1= new Panel(0,0,60,71,new PanelImage("assets/leftnarrow.png","assets/leftnarrow2.png","assets/leftnarrow3.png"));
        leftnarrow1.setBounds(WIDTH/4-210,HEIGHT/2-36,leftnarrow1.width,leftnarrow1.height);
        frame1.add(leftnarrow1);

        Panel leftnarrow2= new Panel(0,0,60,71,new PanelImage("assets/leftnarrow.png","assets/leftnarrow2.png","assets/leftnarrow3.png"));
        leftnarrow2.setBounds(WIDTH/4*3-210,HEIGHT/2-36,leftnarrow2.width,leftnarrow2.height);
        frame1.add(leftnarrow2);

        //Кнопка "Play"
        Panel play= new Panel(0,0,200,111,new PanelImage("assets/play3.png","assets/play4.png","assets/play4.png"));
        play.setBounds(WIDTH/2 - 100,HEIGHT/3*2,play.width,play.height);
        frame1.add(play);

        //Бэкграунд 2й сцены
        Panel background2= new Panel(0,0,WIDTH,HEIGHT,new PanelImage("assets/background.png","assets/background.png","assets/background.png"));
        background2.setBounds(0,0,background2.width,background2.height);
        frame1.add(background2);

        //перерисовываем фрейм
        frame1.repaint();
        /*Добавляем слушатель каждой стрелке. Будем считать нажатия на правую и левую стрелку и, в зависимости от этого,
          увеличивать или уменьшать счётчик. В качестве счётчика используем индикатор панели выбора персонажа.
          Например: изначально panel2.k=0. Индикатору k=0 соответствует изображение чужого => рисуется чужой.
          При нажатии кнопки влево panel2.k уменьшается на 1 => k=-1, которому соответствует дракон => рисуется дракон.
          Если k=min (min =-1) или k=max (max = 1), то дальше уменьшать(увеличивать) счётчик уже нельзя => оответствующая стрелка
          становится желтой (на неё нельзя нажать)*/
        rightnarrow2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panel4.k==1){
                    rightnarrow2.k =-1;}
                else{
                    if (panel4.k==0){
                        rightnarrow2.k=-1;
                    }
                    if (panel4.k==-1){
                        rightnarrow2.k=1;
                        leftnarrow2.k=0;
                    }
                    panel4.k+=1;
                }
                frame1.repaint();


            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel4.k!=1){
                    rightnarrow2.k =1;
                    frame1.repaint();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (panel4.k!=1){
                    rightnarrow2.k =0;
                    frame1.repaint();}
            }
        });
        rightnarrow1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panel2.k==1){
                    rightnarrow1.k =-1;}
                else{
                    if (panel2.k==0){
                        rightnarrow1.k=-1;
                    }
                    if (panel2.k==-1){
                        leftnarrow1.k=0;
                        rightnarrow1.k=1;
                    }
                    panel2.k+=1;
                }
                frame1.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel2.k!=1){
                    rightnarrow1.k =1;
                    frame1.repaint();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (panel2.k!=1){
                    rightnarrow1.k =0;
                    frame1.repaint();}
            }
        });
        leftnarrow1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panel2.k==-1){
                    leftnarrow1.k =-1;}
                else{
                    if (panel2.k==0){
                        leftnarrow1.k=-1;
                    }
                    if (panel2.k==1){
                        rightnarrow1.k=0;
                        leftnarrow1.k=1;
                    }
                    panel2.k-=1;
                }
                frame1.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel2.k!=-1){
                    leftnarrow1.k =1;
                    frame1.repaint();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (panel2.k!=-1){
                    leftnarrow1.k =0;
                    frame1.repaint();}
            }
        });
        leftnarrow2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panel4.k==-1){
                    leftnarrow2.k =-1;}
                else{
                    if (panel4.k==0){
                        leftnarrow2.k=-1;
                    }
                    if (panel4.k==1){
                        rightnarrow2.k=0;
                        leftnarrow2.k=1;
                    }
                    panel4.k-=1;
                }
                frame1.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel4.k!=-1){
                    leftnarrow2.k =1;
                    frame1.repaint();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (panel4.k!=-1){
                    leftnarrow2.k =0;
                    frame1.repaint();}
            }
        });



        //Добавляем слушатель кнопке "Play"
        play.addMouseListener(new MouseAdapter() {
            //Метод, вызываемый, когда мышь находится над панелью
            @Override
            public void mouseEntered(MouseEvent e) {
                play.k=-1;
                frame1.repaint();
            }
            //Метод, вызываемый, когда мышь покинула границы панели
            @Override
            public void mouseExited(MouseEvent e) {
                play.k=0;
                frame1.repaint();
            }
            //Метод, обрабатывающий нажатие мышью на кнопку
            @Override
            public void mouseClicked(MouseEvent e) {
                play.k = 1;
                //Убираем с фрейма все панели
                frame1.remove(panel4);
                frame1.remove(background2);
                frame1.remove(leftnarrow1);
                frame1.remove(panel2);
                frame1.remove(leftnarrow2);
                frame1.remove(rightnarrow1);
                frame1.remove(rightnarrow2);
                frame1.remove(play);
                //Создаём объект класса Game
                Game game=new Game();
                //В зависимости от значения счетчика узнаем, какого персонажа выбрал пользователь, и передаём эту информацию в game
                if (panel2.k ==-1){ game.setN2(1); }
                if (panel2.k ==0){ game.setN2(2); }
                if (panel2.k ==1){ game.setN2(3); }
                if (panel4.k ==-1){ game.setN1(1); }
                if (panel4.k ==0){ game.setN1(2); }
                if (panel4.k ==1){ game.setN1(3); }
                //Инициализация - у всех объектов задаются координаты, масса, скорость и т.п.
                game.init();
                //Задаются размеры окна

                game.setPreferredSize(new Dimension(WIDTH,HEIGHT));

                //Создаётся кнопка "restart" и добавляется на панель
                Panel restart = new Panel(0,0,62,60,new PanelImage("assets/restart1.png","assets/restart2.png","assets/restart2.png"));
                restart.setBounds(WIDTH/2-31,0,restart.width, restart.height);
                frame1.add(restart);
                //установка менеджера компоновки(BorderLayout компонует по сторонам света)
                frame1.setLayout(new BorderLayout());
                //Добавляем game на фрейм(в центр)
                frame1.add(game,BorderLayout.CENTER);
                //Упаковка
                frame1.pack();

                //Добавим слушатель мыши кнопке "restart"
                restart.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        restart.k = 1;
                        frame1.repaint();
                        if(game.isPlayer1win()||game.isPlayer2win()){game.restart();}//елси хотя бы один игрок выиграл, то можно начать игру заново

                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        restart.k = -1;
                        frame1.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        restart.k = 0;
                        frame1.repaint();

                    }
                });
                game.start();//запуск выполнения game
                frame1.repaint();//перерисуем фрейм
            }


        });



    }
}

