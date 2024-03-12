package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.ai.PathFinder;
import cz.cvut.fel.pjv.controller.EventHandler;
import cz.cvut.fel.pjv.controller.KeyHandler;
import cz.cvut.fel.pjv.model.AssetSetter;
import cz.cvut.fel.pjv.model.CollisionChecker;
import cz.cvut.fel.pjv.model.EntityGenerator;
import cz.cvut.fel.pjv.model.Sound;
import cz.cvut.fel.pjv.model.data.SaveLoad;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.model.entity.NullEntity;
import cz.cvut.fel.pjv.model.entity.Player;
import cz.cvut.fel.pjv.model.tile.InteractiveTile;
import cz.cvut.fel.pjv.model.tile.Map;
import cz.cvut.fel.pjv.model.tile.TileManager;
import cz.cvut.fel.pjv.utils.Config;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable {

    //  Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;
    public int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // CONSTANTS NUMBER
    private static final float BILLION_SIZE = 1000000000;
    private static final float MILLION_SIZE = 1000000;

    // WORLD SETTINGS
    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    public int maxMap = 10;
    public int currentMap = 0;
    int frameRate = 60;

    // System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    public Map map = new Map(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] enemy = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][50];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    public NullEntity nullEntity;
    public GameState gameState;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(UIColors.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Sets up the initial state of the game, including game objects and assets.
     */
    public void setupGame() {
        gameState = GameState.TITLE;
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setEnemy();
        aSetter.setInteractiveTile();
        playMusic(GameConstants.SOUND_ZERO);
        stopMusic();
    }

    /**
     * Resets the game, optionally restarting it from the beginning.
     *
     * @param restart `true` to restart the game, `false` to reset it without starting over.
     */
    public void resetGame(boolean restart) {
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();

        if (restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
        }
    }

    /**
     * Starts the game thread to handle game logic and rendering.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The main game loop where game logic is updated and rendering is performed.
     */
    @Override
    public void run() {
        double drawInterval = BILLION_SIZE / frameRate; //  0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            update(); // Update
            repaint(); // Draw
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / MILLION_SIZE;
                if (remainingTime < 0) { remainingTime = 0; }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game logic, including player and entity behaviors.
     */
    public void update() {

        if (gameState == GameState.PLAY) {
            // PLAYER
            player.update();

            // NPS
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != nullEntity) { npc[currentMap][i].update(); }
            }

            // ENEMY
            for (int i = 0; i < enemy[1].length; i++) {
                if (enemy[currentMap][i] != nullEntity) {
                    if (enemy[currentMap][i].alive && !enemy[currentMap][i].dying) { enemy[currentMap][i].update(); }
                    if (!enemy[currentMap][i].alive) {
                        enemy[currentMap][i].checkDrop();
                        enemy[currentMap][i] = nullEntity;
                    }
                }
            }

            // PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != nullEntity) {
                    if (projectile[currentMap][i].alive) { projectile[currentMap][i].update(); }
                    if (!projectile[currentMap][i].alive) { projectile[currentMap][i] = nullEntity; }
                }
            }

            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != nullEntity) {
                    if (particleList.get(i).alive) { particleList.get(i).update(); }
                    if (!particleList.get(i).alive) { particleList.remove(i); }
                }
            }

            for (int i = 0; i < iTile[1].length; i++)  {
                if (iTile[currentMap][i] != null) { iTile[currentMap][i].update(); }
            }
        }
    }

    /**
     * Renders the game, including tiles, entities, and the user interface.
     *
     * @param g The graphics context used for rendering.
     */
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState == GameState.TITLE) { ui.draw(g2); } // Title screen
        else if (gameState == GameState.MAP) { map.drawFullMapScreen(g2); } // Map screen

        else {
            //  Draw Tile
            tileM.draw(g2);

            // Interactive Tile
            for (int i = 0; i < iTile[1].length ; i++) {
                if (iTile[currentMap][i] != null) { iTile[currentMap][i].draw(g2); }
            }

            entityList.add(player); // Add entities to the list

            for (int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) { entityList.add(npc[currentMap][i]); }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) { entityList.add(obj[currentMap][i]); }
            }

            for (int i = 0; i < enemy[1].length; i++) {
                if (enemy[currentMap][i] != null) { entityList.add(enemy[currentMap][i]); }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) { entityList.add(projectile[currentMap][i]); }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) { entityList.add(particleList.get(i)); }
            }

            for (int i = 0; i < entityList.size(); i++) { entityList.get(i).draw(g2); } // Draw Entities
            entityList.clear(); // Empty entity list
            map.drawMiniMap(g2); // Draw Mini map
            ui.draw(g2); // Draw UI
        }
        g2.dispose();
    }

    /**
     * Plays background music with the specified index.
     *
     * @param i The index of the background music to play.
     */
    public void playMusic (int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    /**
     * Stops the currently playing background music.
     */
    public void stopMusic () {
        music.stop();
    }

    /**
     * Plays a sound effect with the specified index.
     *
     * @param i The index of the sound effect to play.
     */
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public void setMaxWorldCol(int maxWorldCol) {
        this.maxWorldCol = maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public void setMaxWorldRow(int maxWorldRow) {
        this.maxWorldRow = maxWorldRow;
    }

    public int getMaxMap() {
        return maxMap;
    }

    public void setMaxMap(int maxMap) {
        this.maxMap = maxMap;
    }
}
