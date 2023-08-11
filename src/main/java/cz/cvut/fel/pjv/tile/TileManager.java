package cz.cvut.fel.pjv.tile;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    /**
     * Manages tiles for the game, initializing tile array, map tile numbers, and loading maps.
     *
     * @param gp The GamePanel instance associated with the tile manager.
     */
    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[50];

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/new.txt", 0);
//        loadMap("/maps/interior.txt", 1);
        loadMap("/maps/pjv.txt", 1);


    }

    public void getTileImage() {

        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
        setup(6, "tree2", true);
        setup(7, "teleport", false);
        setup(8, "sign", false);
        setup(9, "danger", false);
        setup(10, "fountain", true);
        setup(11, "drytree", true);


        setup(12, "hut", false);
        setup(13, "floor", false);
        setup(14, "table", true);
        setup(15, "h130", false);
        setup(16, "laptop", true);
        setup(17, "stairs", false);
        setup(18, "whiteboard", false);
        setup(19, "bonbon", true);
        setup(20, "bonbon2", true);
        setup(21, "bonbon3", true);
        setup(22, "umbrella", false);



    }

    /**
     * Sets up a tile with the provided parameters such as index, image name, and collision flag.
     *
     * @param index The index of the tile in the tile array.
     * @param imageName The name of the image file associated with the tile.
     * @param collision A flag indicating whether the tile has collision.
     */
    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a map from the specified file path and populates the mapTileNum array with tile numbers.
     *
     * @param filePath The path to the map file to be loaded.
     * @param map The index of the map in the mapTileNum array.
     */
    public void loadMap(String filePath, int map) {
        try {

            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {

                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the visible map tiles on the screen, considering player's position for efficient rendering.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw (Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {

                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }

}
