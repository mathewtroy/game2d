package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.data.LevelData;
import cz.cvut.fel.pjv.model.data.Position;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.model.armor.HelmetGerman;
import cz.cvut.fel.pjv.model.enemy.Tank;
import cz.cvut.fel.pjv.model.entity.Entity;
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
import java.util.List;
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
    private static final String UNSUPPORTED_ENEMY_CLASS_MESSAGE = "Unsupported enemy class: ";
    private static final String ERROR_INSTANTIATING_ENEMY = "Error instantiating enemy class: ";
    private static final String ERROR_INITIALIZING_MSG = "Error initializing Objects ";
    private static final String UNSUPPORTED_NPC_CLASS = "Unsupported NPC class: ";
    private static final String ERROR_INITIALIZING_NPC = "Error initializing NPC ";
    private static final String COLON_SPACE = ": ";

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
            i = initializeEntities(levelData.getCoins(), CoinGold.class, mapNum, i);
            i = initializeEntities(levelData.getKeys(), Key.class, mapNum, i);
            i = initializeEntities(levelData.getRams(), Ram.class, mapNum, i);
            i = initializeEntities(levelData.getHelmets(), HelmetGerman.class, mapNum, i);
            i = initializeEntities(levelData.getFirstAids(), FirstAid.class, mapNum, i);
            i = initializeEntities(levelData.getAmmunitions(), Ammunition.class, mapNum, i);
            i = initializeEntities(levelData.getHearts(), Heart.class, mapNum, i);
            i = initializeEntities(levelData.getBoots(), Boots.class, mapNum, i);
            i = initializeEntities(levelData.getDoors(), Door.class, mapNum, i);
            i = initializeEntities(levelData.getChests(), Chest.class, mapNum, i);

            LevelData levelData2 = objectMapper.readValue(new File(LEVEL_SECOND_JSON), LevelData.class);
            i = initializeEntities(levelData2.getKeys(), Key.class, 1, i);

            LevelData levelData3 = objectMapper.readValue(new File(LEVEL_THIRD_JSON), LevelData.class);
            i = initializeEntities(levelData3.getCoins(), CoinGold.class, 2, i);
            i = initializeEntities(levelData3.getKeys(), Key.class, 2, i);

        } catch (IOException ex) {
            logger.warning(ERROR_READING_JSON_FILE + ex.getMessage());
        }
    }

    private int initializeEntities(List<Position> positions, Class<? extends Entity> entityClass, int mapNum, int startIndex) {
        int i = startIndex;
        for (Position position : positions) {
            try {
                Entity entity = entityClass.getConstructor(GamePanel.class).newInstance(gp);
                entity.worldX = gp.tileSize * position.getX();
                entity.worldY = gp.tileSize * position.getY();
                gp.obj[mapNum][i++] = entity;
            } catch (ReflectiveOperationException e) {
                logger.severe(ERROR_INITIALIZING_MSG + entityClass.getSimpleName() + COLON_SPACE + e.getMessage());
            }
        }
        return i;
    }

    /**
     * Initializes NPCs on the current map
     */
    public void setNPC() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LevelData levelDataFirst = objectMapper.readValue(new File(LEVEL_FIRST_JSON), LevelData.class);
            initializeNPCs(levelDataFirst, 0, OldMan.class);
            LevelData levelDataSecond = objectMapper.readValue(new File(LEVEL_SECOND_JSON), LevelData.class);
            initializeNPCs(levelDataSecond, 1, Merchant.class);
        } catch (IOException ex) {
            logger.warning(ERROR_READING_JSON_FILE + ex.getMessage());
        }
    }

    private void initializeNPCs(LevelData levelData, int mapNum, Class<? extends Entity> npcClass) {
        int i = 0;
        List<Position> positions;
        if (npcClass == Merchant.class) { positions = levelData.getMerchants(); }
        else if (npcClass == OldMan.class) { positions = levelData.getOldMans(); }
        else {
            logger.severe(UNSUPPORTED_NPC_CLASS + npcClass.getName());
            return;
        }
        for (Position position : positions) {
            try {
                Entity npc = npcClass.getConstructor(GamePanel.class).newInstance(gp);
                npc.worldX = gp.tileSize * position.getX();
                npc.worldY = gp.tileSize * position.getY();
                gp.npc[mapNum][i++] = npc;
            } catch (Exception e) {
                logger.severe(ERROR_INITIALIZING_NPC + npcClass.getSimpleName() + COLON_SPACE + e.getMessage());
            }
        }
    }

    /**
     * Initializes enemies on the current map
     */
    public void setEnemy() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LevelData levelData1 = objectMapper.readValue(new File(LEVEL_FIRST_JSON), LevelData.class);
            initializeEnemies(levelData1, 0, Tank.class);
            LevelData levelData3 = objectMapper.readValue(new File(LEVEL_THIRD_JSON), LevelData.class);
            initializeEnemies(levelData3, 2, Ghost.class);
        } catch (IOException ex) {
            logger.warning(ERROR_READING_JSON_FILE + ex.getMessage());
        }
    }

    private void initializeEnemies(LevelData levelData, int mapNum, Class<? extends Entity> enemyClass) {
        int i = 0;
        List<Position> positions;

        if (enemyClass == Tank.class) { positions = levelData.getTanks(); }
        else if (enemyClass == Ghost.class) { positions = levelData.getGhosts(); }
        else {
            String errorMessage = UNSUPPORTED_ENEMY_CLASS_MESSAGE + enemyClass.getName();
            logger.severe(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        for (Position position : positions) {
            try {
                Entity enemy = enemyClass.getConstructor(GamePanel.class).newInstance(gp);
                enemy.worldX = gp.tileSize * position.getX();
                enemy.worldY = gp.tileSize * position.getY();
                gp.enemy[mapNum][i++] = enemy;
            } catch (Exception e) {
                logger.severe(ERROR_INSTANTIATING_ENEMY + enemyClass.getName() + COLON_SPACE + e.getMessage());
            }
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
