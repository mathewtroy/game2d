package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.data.LevelData;
import cz.cvut.fel.pjv.model.data.Position;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.model.armor.HelmetGerman;
import cz.cvut.fel.pjv.model.enemy.Tank;
import cz.cvut.fel.pjv.model.entity.Merchant;
import cz.cvut.fel.pjv.model.entity.OldMan;
import cz.cvut.fel.pjv.model.enemy.Ghost;
import cz.cvut.fel.pjv.model.object.*;
import cz.cvut.fel.pjv.model.refill.Ammunition;
import cz.cvut.fel.pjv.model.refill.FirstAid;
import cz.cvut.fel.pjv.model.tile.DryTree;
import cz.cvut.fel.pjv.model.vitality.Heart;
import cz.cvut.fel.pjv.model.weapon.Ram;
import cz.cvut.fel.pjv.view.GamePanel;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The AssetSetter is responsible for initializing game objects and NPCs on different maps
 *
 */
public class AssetSetter {

    private static final String LEVEL_FIRST_JSON = "level1data.json";
    private static final String LEVEL_SECOND_JSON = "level2data.json";
    private static final String LEVEL_THIRD_JSON = "level3data.json";
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    public static final String ERROR_READING_JSON_FILE = "Error reading JSON file: ";

    GamePanel gp;

    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Initializes game objects on the current map
     */
    public void setObject() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            int mapNum = 0;
            int i = 0;

            LevelData levelData = objectMapper.readValue(new File(LEVEL_FIRST_JSON), LevelData.class);
            i = initializeCoinGold(levelData, 0, i);
            i = initializeKey(levelData, 0, i);
            i = initializeRam(levelData, 0, i);
            i = initializeHelmetGerman(levelData, 0, i);
            i = initializeFirstAids(levelData, 0, i);
            i = initializeAmmunition(levelData, 0, i);
            i = initializeHearts(levelData, 0, i);
            i = initializeBoots(levelData, 0, i);
            i = initializeDoors(levelData, 0, i);
            i = initializeChest(levelData, 0, i);

            LevelData levelData2 = objectMapper.readValue(new File(LEVEL_SECOND_JSON), LevelData.class);
            i = initializeKey(levelData2, 1, i);

            LevelData levelData3 = objectMapper.readValue(new File(LEVEL_THIRD_JSON), LevelData.class);
            i = initializeCoinGold(levelData3, 2, i);
            i = initializeKey(levelData3, 2, i);

        } catch (IOException ex) {
            logger.warning(ERROR_READING_JSON_FILE + ex.getMessage());
        }

    }

    private int initializeCoinGold(LevelData levelData, int mapNum, int i) {
        for (Position coinPosition : levelData.getCoins()) {
            gp.obj[mapNum][i] = new CoinGold(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * coinPosition.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * coinPosition.getY();
            i++;
        }
        return i;
    }

    private int initializeKey(LevelData levelData, int mapNum, int i) {
        for (Position keyPosition : levelData.getKeys()) {
            gp.obj[mapNum][i] = new Key(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * keyPosition.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * keyPosition.getY();
            i++;
        }
        return i;
    }

    private int initializeRam(LevelData levelData, int mapNum, int i) {
        for (Position ramPosition : levelData.getRams()) {
            gp.obj[mapNum][i] = new Ram(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * ramPosition.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * ramPosition.getY();
            i++;
        }
        return i;
    }

    private int initializeHelmetGerman(LevelData levelData, int mapNum, int i) {
        for (Position helmetPosition : levelData.getHelmets()) {
            gp.obj[mapNum][i] = new HelmetGerman(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * helmetPosition.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * helmetPosition.getY();
            i++;
        }
        return i;
    }

    private int initializeFirstAids(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getFirstAids()) {
            gp.obj[mapNum][i] = new FirstAid(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
        return i;
    }

    private int initializeAmmunition(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getAmmunitions()) {
            gp.obj[mapNum][i] = new Ammunition(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
        return i;
    }

    private int initializeHearts(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getHearts()) {
            gp.obj[mapNum][i] = new Heart(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
        return i;
    }

    private int initializeBoots(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getBoots()) {
            gp.obj[mapNum][i] = new Boots(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
        return i;
    }

    private int initializeDoors(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getDoors()) {
            gp.obj[mapNum][i] = new Door(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
        return i;
    }

    private int initializeChest(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getChests()) {
            gp.obj[mapNum][i] = new Chest(gp);
            gp.obj[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.obj[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
        return i;
    }

    /**
     * Initializes NPCs on the current map
     */
    public void setNPC() {
        ObjectMapper objectMapper = new ObjectMapper();
        try { initializeOldmans(objectMapper.readValue(new File(LEVEL_FIRST_JSON), LevelData.class), 0, 0);
            initializeMerchants(objectMapper.readValue(new File(LEVEL_SECOND_JSON), LevelData.class), 1, 0);
        } catch (IOException ex) {
            logger.warning(ERROR_READING_JSON_FILE + ex.getMessage());
        }
    }

    private void initializeMerchants(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getMerchants()) {
            gp.npc[mapNum][i] = new Merchant(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.npc[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
    }

    private void initializeOldmans(LevelData levelData, int mapNum, int i) {
        for (Position position : levelData.getOldMans()) {
            gp.npc[mapNum][i] = new OldMan(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize * position.getX();
            gp.npc[mapNum][i].worldY = gp.tileSize * position.getY();
            i++;
        }
    }

    /**
     * Initializes enemies on the current map
     */
    public void setEnemy() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            initializeTanks(objectMapper.readValue(new File(LEVEL_FIRST_JSON), LevelData.class), 0, 0);
            initializeGhosts(objectMapper.readValue(new File(LEVEL_THIRD_JSON), LevelData.class), 2, 0);
        } catch (IOException ex) { logger.warning(ERROR_READING_JSON_FILE + ex.getMessage()); }
    }

    private void initializeTanks(LevelData levelData, int mapNum, int i) {
        for (Position tankPosition : levelData.getTanks()) {
            gp.enemy[mapNum][i] = new Tank(gp);
            gp.enemy[mapNum][i].worldX = gp.tileSize * tankPosition.getX();
            gp.enemy[mapNum][i].worldY = gp.tileSize * tankPosition.getY();
            i++;
        }
    }

    private void initializeGhosts(LevelData levelData, int mapNum, int i) {
        int ghostIndex = 0;
        for (Position ghostPosition : levelData.getGhosts()) {
            gp.enemy[mapNum][i] = new Ghost(gp);
            gp.enemy[mapNum][i].worldX = gp.tileSize * ghostPosition.getX();
            gp.enemy[mapNum][i].worldY = gp.tileSize * ghostPosition.getY();
            i++;
            ghostIndex++;
        }
    }


    /**
     * Initializes interactive tiles on the current map
     */
    public void setInteractiveTile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LevelData levelData = objectMapper.readValue(new File(LEVEL_FIRST_JSON), LevelData.class);
            initializeDryTrees(levelData, 0);
        } catch (IOException ex) {
            logger.warning(ERROR_READING_JSON_FILE + ex.getMessage());
        }
    }

    private void initializeDryTrees(LevelData levelData, int mapNum) {
        int i = 0;
        for (Position position : levelData.getDryTrees()) {
            gp.iTile[mapNum][i] = new DryTree(gp, position.getX(), position.getY());
            i++;
        }
    }
}
