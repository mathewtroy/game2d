package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

// CONSTANTS NUMBER
    private static final float BILLION_SIZE = 1000000000;
    private static final float MILLION_SIZE = 1000000;




    //  WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;



    //    FPS
    int FPS = 60;

//    SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

//    ENTITY and OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

//    GAME STATE
    public int gameState;

    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();

//        playMusic(0);
//        stopMusic();

        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
    @Override
    public void run() {
        //TODO add smth

        double drawInterval = BILLION_SIZE / FPS;
        //  0.01666 seconds

        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

//        UPDATE
            update();

//        DRAW
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / MILLION_SIZE;

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

        if (gameState == playState) {

            // PLAYER
            player.update();

            // NPS
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            // MONSTER
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if (monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }
        }

        if (gameState == pauseState) {

            //TODO nothong
        }


    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

//  DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

//  TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }

//  OTHERS
        else {
            //  TILE
            tileM.draw(g2);

            //  ADD ENTITES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });


            // DRAW ENTITES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            //  UI
            ui.draw(g2);

        }



//  DEBUG
        if (keyH.checkDrawTime == true ) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();
    }


    public void playMusic (int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic () {

        music.stop();
    }

    public void playSE(int i) {

        se.setFile(i);
        se.play();

    }

}
