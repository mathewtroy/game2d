package cz.cvut.fel.pjv.model.monster;

import cz.cvut.fel.pjv.model.object.OBJ_Heart;
import cz.cvut.fel.pjv.model.object.OBJ_ManaCrystal;
import cz.cvut.fel.pjv.model.object.OBJ_Potion_Red;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.Random;
import java.util.logging.Logger;

public class MON_Ghost extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_GHOST = "Missing image of the GHOST";
    private static final String monsterName = "Ghost";

    GamePanel gp;

    public MON_Ghost (GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        setName(monsterName);
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 30;   // added more life
        life = maxLife;
        attack = 5;
        defense = 3;
        setExp(5);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setupGhostImage();
    }

    /**
     * Sets up the images for the Ghost monster.
     */
    private void setupGhostImage() {
        try {
            up1 = setup("/monster/ghost_down_1", gp.tileSize, gp.tileSize);
            up2 = setup("/monster/ghost_down_2", gp.tileSize, gp.tileSize);
            down1 = setup("/monster/ghost_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/monster/ghost_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/monster/ghost_down_1", gp.tileSize, gp.tileSize);
            left2 = setup("/monster/ghost_down_2", gp.tileSize, gp.tileSize);
            right1 = setup("/monster/ghost_down_1", gp.tileSize, gp.tileSize);
            right2 = setup("/monster/ghost_down_2", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_GHOST);
        }
    }

    /**
     * Sets the action and behavior for the Ghost monster.
     */
    public void setAction () {
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < MON_Green.CLOSE_DISTANCE) {
            handleRandomPathSelection();
        }

        if (onPath && tileDistance > MON_Green.FAR_DISTANCE) {
            handleRandomPathSelection();
        }

        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
        } else {
            handleRandomDirection();
        }
    }

    /**
     * Handles the selection of a random path.
     */
    private void handleRandomPathSelection() {
        int i = new Random().nextInt(MON_Green.HIGH_PROBABILITY) + 1;
        if (i > MON_Green.MEDIUM_LOW_PROBABILITY) {
            onPath = true;
        }
    }

    /**
     * Handles the selection of a random direction when not on a path.
     */
    private void handleRandomDirection() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(MON_Green.HIGH_PROBABILITY) + 1; // pick up a number from 1 to 100

            if (i <= MON_Green.LOW_PROBABILITY) {
                direction = "up";
            }
            if (i > MON_Green.LOW_PROBABILITY && i <= MON_Green.MEDIUM_LOW_PROBABILITY) {
                direction = "down";
            }
            if (i > MON_Green.MEDIUM_LOW_PROBABILITY && i <= MON_Green.MEDIUM_HIGH_PROBABILITY) {
                direction = "left";
            }
            if (i > MON_Green.MEDIUM_HIGH_PROBABILITY) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    /**
     * Handles the reaction to damage received by the Ghost monster.
     */
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
        onPath = true;
    }

    /**
     * Checks and determines the item drop for the Ghost monster.
     */
    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(MON_Green.HIGH_PROBABILITY)+1;

        // SET THE MONSTER DROP
        if (i < MON_Green.MEDIUM_LOW_PROBABILITY) { dropItem(new OBJ_Potion_Red(gp)); }

        if (i >= MON_Green.MEDIUM_LOW_PROBABILITY && i < MON_Green.MEDIUM_HIGH_PROBABILITY) { dropItem(new OBJ_Heart(gp)); }

        if (i >= MON_Green.MEDIUM_HIGH_PROBABILITY && i < MON_Green.HIGH_PROBABILITY) { dropItem(new OBJ_ManaCrystal(gp)); }

    }
}
