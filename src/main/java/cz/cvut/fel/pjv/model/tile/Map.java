package cz.cvut.fel.pjv.model.tile;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.view.UIColors;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager {

    GamePanel gp;
    protected BufferedImage[] worldMap;
    public boolean miniMapOn = false;
    private static final String OPEN_CLOSE_MAP = "Press M to close";

    /**
     * Manages tiles for the game, initializing tile array, map tile numbers, and loading maps.
     *
     * @param gp The GamePanel instance associated with the tile manager.
     */
    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }

    /**
     * Creates the world map by assembling tiles from mapTileNum into images.
     */
    public void createWorldMap() {

        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        for (int i = 0; i < gp.maxMap; i++) {

            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                int tileNum = mapTileNum[i][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;

                g2.drawImage(tile[tileNum].image, x, y, null);

                col++;
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }

    /**
     * Draws the full map screen, including the map, player, and hints.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawFullMapScreen(Graphics2D g2) {

        // Background color
        g2.setColor(UIColors.BLACK);
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        // Draw MAP
        int width = 500;
        int height = 500;
        int x = gp.screenWidth/2 - width/2;
        int y = gp.screenHeight/2 - height/2;
        g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

        // Draw player
        double scale = (double) (gp.tileSize * gp.maxWorldCol)/width;
        int playerX = (int) (x + gp.player.worldX/scale);
        int playerY = (int) (y + gp.player.worldY/scale);
        int playerSize = (int) (gp.tileSize/scale);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize,null);

        // HINT
        g2.setFont(gp.ui.arial_40.deriveFont(32f));
        // think about another font
        g2.setColor(UIColors.WHITE);
        g2.drawString(OPEN_CLOSE_MAP, 720, 550);

    }

    /**
     * Draws the mini-map if it's enabled.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void drawMiniMap(Graphics2D g2) {

        if (!miniMapOn) {

            // Draw map
            int width = 200;
            int height = 200;
            int x = gp.screenWidth - width - 50;
            int y = 50;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

            // Draw player
            double scale = (double) (gp.tileSize * gp.maxWorldCol)/width;
            int playerX = (int) (x + gp.player.worldX/scale);
            int playerY = (int) (y + gp.player.worldY/scale);
            int playerSize = gp.tileSize/3;
            g2.drawImage(gp.player.down1, playerX-6, playerY-6, playerSize, playerSize,null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
