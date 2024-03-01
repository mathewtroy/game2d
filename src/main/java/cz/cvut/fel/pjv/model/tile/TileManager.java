package cz.cvut.fel.pjv.model.tile;

import cz.cvut.fel.pjv.controller.MapConstants;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.utils.UtilityTool;
import cz.cvut.fel.pjv.view.UIColors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;


public class TileManager {

    GamePanel gp;
    private static final String TILE_PATH = "/tiles/";
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_NEGATIVE_NUMBER_MAP_VALUE = "Use positive number!";
    private static final String LOGGER_MAX_NUMBER_MAP_VALUE = "Use positive number till 22";
    private static final String LOGGER_WRONG_MAP_VALUE = "Are you idiot? Use only numbers!!!";
    private static final String LOGGER_INCORRECT_COLUMNS = "Incorrect number of columns in row ";
    private static final String LOGGER_EXPECTED = ". Expected ";
    private static final String LOGGER_BUT_GOT = ", but got ";
    private static final String LOGGER_DOT = ".";
    private static final String LOGGER_ERROR_LOADING_MAP = "Error loading map: ";

    public Tile[] tile;
    public int[][][] mapTileNum;
    boolean drawPath = true;

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
        loadMap(MapConstants.MAP_PATH_NEW2, MapConstants.MAP_NEW);
        loadMap(MapConstants.MAP_PATH_PJV, MapConstants.MAP_PJV);
        loadMap(MapConstants.MAP_PATH_GOLD, MapConstants.MAP_GOLD);
    }

    private void getTileImage() {
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
        setup(12, "entry", false);
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
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream(TILE_PATH + imageName + Entity.PNG_FORMAT)));
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
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int row = 0;

            String line;
            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                String[] numbers = line.split(" ");

                // Check for the number of elements in a line
                if (numbers.length != gp.maxWorldCol) {
                    logger.warning(LOGGER_INCORRECT_COLUMNS + row + LOGGER_EXPECTED
                            + gp.maxWorldCol + LOGGER_BUT_GOT + numbers.length + LOGGER_DOT);
                    break; // Stop reading the file to avoid errors due to incorrect format
                }

                for (int col = 0; col < numbers.length; col++) {
                    try {
                        int num = Integer.parseInt(numbers[col]);

                        if (num < 0) {
                            logger.warning(LOGGER_NEGATIVE_NUMBER_MAP_VALUE);
                            num = 4; // Change value to "wall" if number is negative
                        } else if (num > 22) {
                            logger.warning(LOGGER_MAX_NUMBER_MAP_VALUE);
                            num = 2; // Change value to "water" if number is larger then 22
                        }

                        mapTileNum[map][col][row] = num;
                    } catch (NumberFormatException e) {
                        logger.warning(LOGGER_WRONG_MAP_VALUE);
                        mapTileNum[map][col][row] = 4; // If there is an error in the data, use "tree2"
                    }
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            logger.warning(LOGGER_ERROR_LOADING_MAP + e.getMessage());
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

        if (drawPath) {
            g2.setColor(UIColors.PATH_COLOR);
            for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                g2.fillRect(screenX,screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
}
