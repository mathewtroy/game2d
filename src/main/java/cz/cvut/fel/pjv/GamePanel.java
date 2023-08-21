package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.ai.PathFinder;
import cz.cvut.fel.pjv.data.SaveLoad;
import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.tile.InteractiveTile;
import cz.cvut.fel.pjv.tile.Map;
import cz.cvut.fel.pjv.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static cz.cvut.fel.pjv.Sound.SOUND_ZERO;

public class GamePanel extends JPanel implements Runnable {


    //  SCREEN Settings
    //    16x16 px
    final int originalTileSize = 16;
    final int scale = 3;

    // 48x48 px
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;

    // 960 px
    public final int screenWidth = tileSize * maxScreenCol;

    // 576 px
    public final int screenHeight = tileSize * maxScreenRow;

    // CONSTANTS NUMBER
    private static final float BILLION_SIZE = 1000000000;
    private static final float MILLION_SIZE = 1000000;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    //    FPS
    int FPS = 60;

//    SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    Thread gameThread;

//    ENTITY and OBJECT
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][50];
    // public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

//    GAME STATE
    public GameState gameState;

    public enum GameState {
        TITLE,
        PLAY,
        PAUSE,
        DIALOGUE,
        CHARACTER,
        OPTION,
        GAME_OVER,
        TRANSITION,
        TRADE,
        MAP
    }



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
        aSetter.setInteractiveTile();
        playMusic(SOUND_ZERO);
        stopMusic();
        gameState = GameState.TITLE;
    }

    public void resetGame(boolean restart) {
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if (restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = BILLION_SIZE / FPS;
        //  0.01666 seconds

        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // UPDATE
            update();

            // DRAW
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

        if (gameState == GameState.PLAY) {

            // PLAYER
            player.update();

            // NPS
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // MONSTER
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }


            // PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }



            for (int i = 0; i < iTile[1].length; i++)  {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //  DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        //  TITLE SCREEN
        if (gameState == GameState.TITLE) {
            ui.draw(g2);
        }

        // MAP SCREEN
        else if (gameState == GameState.MAP) {
            map.drawFullMapScreen(g2);
        }


        //  OTHERS
        else {
            //  TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length ; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //  ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });


            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // MINI MAP
            map.drawMiniMap(g2);

            //  UI
            ui.draw(g2);

        }



//  DEBUG
        if (keyH.checkDrawTime) {
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
