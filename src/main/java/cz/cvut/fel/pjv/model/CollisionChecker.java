package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.controller.MapConstants;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;

import java.util.logging.Logger;

public class CollisionChecker {

    GamePanel gp;
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_OUT_OF_BOUNDARIES = "You are out of the boundaries";

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Checks for collision between an entity and map tiles.
     *
     * @param entity The entity for which collision is being checked.
     */
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        if(entityLeftCol < 0 || entityRightCol >= gp.maxWorldCol || entityTopRow < 0 || entityBottomRow >= gp.maxWorldRow) {
            logger.warning(LOGGER_OUT_OF_BOUNDARIES);
            return; // Cancel method execution to avoid further testing
        }

        int tileNum1 = 0, tileNum2 = 0;

        try {
            switch (entity.direction) {
                case MapConstants.UP:
                    entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                    if (entityTopRow < 0) throw new IndexOutOfBoundsException(LOGGER_OUT_OF_BOUNDARIES);
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                    break;

                case MapConstants.DOWN:
                    entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                    if (entityBottomRow >= gp.maxWorldRow) throw new IndexOutOfBoundsException(LOGGER_OUT_OF_BOUNDARIES);
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                    break;

                case MapConstants.LEFT:
                    entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                    if (entityLeftCol < 0) throw new IndexOutOfBoundsException(LOGGER_OUT_OF_BOUNDARIES);
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                    break;

                case MapConstants.RIGHT:
                    entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                    if (entityRightCol >= gp.maxWorldCol) throw new IndexOutOfBoundsException(LOGGER_OUT_OF_BOUNDARIES);
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                    break;
            }

            // Run a collision check using tileNum1 and tileNum2
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } catch (IndexOutOfBoundsException e) {
            logger.warning(e.getMessage());
            entity.collisionOn = false; // Do not set CollisionOn to true to avoid getting stuck
        }

    }


    /**
     * Checks for collision between an entity and game objects.
     *
     * @param entity The entity for which collision is being checked.
     * @param player Indicates whether the entity is the player.
     * @return The index of the collided object, or MAX_COST if no collision occurred.
     */
    public int checkObject(Entity entity, boolean player) {

        int index = GameConstants.MAX_COST;

        for (int i = 0; i < gp.obj[1].length; i++) {

            if (gp.obj[gp.currentMap][i] != gp.nullEntity) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gp.obj[gp.currentMap][i].solidArea.x
                        = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y
                        = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                String direction = entity.direction;
                adjustSolidAreaPosition(entity, direction);

                if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                    if (gp.obj[gp.currentMap][i].collision) { entity.collisionOn = true; }
                    if (player) { index = i; }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Checks for collision between an entity and other entities (NPCs or enemies).
     *
     * @param entity The entity for which collision is being checked.
     * @param target The array of entities to check for collision with.
     * @return The index of the collided entity, or MAX_COST if no collision occurred.
     */
    // NPC or ENEMY
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = GameConstants.MAX_COST;

        for (int i = 0; i < target[1].length; i++) {

            if (target[gp.currentMap][i] != gp.nullEntity) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[gp.currentMap][i].solidArea.x
                        = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y
                        = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                String direction = entity.direction;
                adjustSolidAreaPosition(entity, direction);

                if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                    if (target[gp.currentMap][i] != entity) { entity.collisionOn = true; index = i; }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Checks for collision between the specified entity and the player's solid area.
     *
     * @param entity The entity for which collision is being checked.
     * @return True if there is collision between the entity and the player's solid area, otherwise false.
     */
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;
        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        String direction = entity.direction;
        adjustSolidAreaPosition(entity, direction);

        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }

    /**
     * Adjusts the solid area position of an entity based on its direction.
     *
     * @param entity The entity for which the solid area position is being adjusted.
     * @param direction The direction in which the entity is moving.
     */
    private void adjustSolidAreaPosition(Entity entity, String direction) {
        switch (direction) {
            case MapConstants.UP: entity.solidArea.y -= entity.speed; break;
            case MapConstants.DOWN: entity.solidArea.y += entity.speed; break;
            case MapConstants.LEFT: entity.solidArea.x -= entity.speed; break;
            case MapConstants.RIGHT: entity.solidArea.x += entity.speed; break;
        }
    }
}
