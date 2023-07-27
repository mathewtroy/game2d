package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

//  SCREEN Settings
//    16x16 px
    final int originalTileSize = 16;
    final int scale = 3;

//    48x48 px
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

//    768 px
    public final int screenWidth = tileSize * maxScreenCol;

//    576 px
    public final int screenHeight = tileSize * maxScreenRow;



//  WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;



    //    FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
    @Override
    public void run() {
        //TODO add smth

        double drawInterval = 1000000000 / FPS;
        //  0.01666 seconds

        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

//        UPDATE
            update();

//        DRAW
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();


    }

}
