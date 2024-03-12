package cz.cvut.fel.pjv.model.enemy;

import cz.cvut.fel.pjv.controller.MapConstants;
import cz.cvut.fel.pjv.model.vitality.Heart;
import cz.cvut.fel.pjv.model.vitality.Mana;
import cz.cvut.fel.pjv.model.refill.FirstAid;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.Random;
import java.util.logging.Logger;

    public class Ghost extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_GHOST = "Missing image of the GHOST";
    private static final String GHOST_PATH_1 = "/enemy/ghost_down_1";
    private static final String GHOST_PATH_2 = "/enemy/ghost_down_2";
    private static final String enemyName = "Ghost";

    GamePanel gp;

    public Ghost(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_enemy;
        setName(enemyName);
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
     * Sets up the images for the Ghost enemy.
     */
    private void setupGhostImage() {
        try {
            up1 = setup(GHOST_PATH_1, gp.tileSize, gp.tileSize);
            up2 = setup(GHOST_PATH_2, gp.tileSize, gp.tileSize);
            down1 = setup(GHOST_PATH_1, gp.tileSize, gp.tileSize);
            down2 = setup(GHOST_PATH_2, gp.tileSize, gp.tileSize);
            left1 = setup(GHOST_PATH_1, gp.tileSize, gp.tileSize);
            left2 = setup(GHOST_PATH_2, gp.tileSize, gp.tileSize);
            right1 = setup(GHOST_PATH_1, gp.tileSize, gp.tileSize);
            right2 = setup(GHOST_PATH_2, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_GHOST + e.getMessage());
        }
    }

    /**
     * Sets the action and behavior for the Ghost enemy.
     */
    public void setAction () {
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;
        if (!onPath && tileDistance < Tank.CLOSE_DISTANCE) { handleRandomPathSelection(); }
        if (onPath && tileDistance > Tank.FAR_DISTANCE) { handleRandomPathSelection(); }
        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
        } else { handleRandomDirection(); }
    }

    /**
     * Handles the selection of a random path.
     */
    private void handleRandomPathSelection() {
        int i = new Random().nextInt(Tank.HIGH_PROBABILITY) + 1;
        if (i > Tank.MEDIUM_LOW_PROBABILITY) {onPath = true;}
    }

    /**
     * Handles the selection of a random direction when not on a path.
     */
    private void handleRandomDirection() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(Tank.HIGH_PROBABILITY) + 1; // pick up a number from 1 to 100
            if (i <= Tank.LOW_PROBABILITY) { direction = MapConstants.UP; }
            if (i > Tank.LOW_PROBABILITY && i <= Tank.MEDIUM_LOW_PROBABILITY) { direction = MapConstants.DOWN; }
            if (i > Tank.MEDIUM_LOW_PROBABILITY && i <= Tank.MEDIUM_HIGH_PROBABILITY) { direction = MapConstants.LEFT; }
            if (i > Tank.MEDIUM_HIGH_PROBABILITY) { direction = MapConstants.RIGHT; }
            actionLockCounter = 0;
        }
    }

    /**
     * Handles the reaction to damage received by the Ghost enemy.
     */
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
        onPath = true;
    }

    /**
     * Checks and determines the item drop for the Ghost enemy.
     */
    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(Tank.HIGH_PROBABILITY)+1;
        // SET THE ENEMY DROP
        if (i < Tank.MEDIUM_LOW_PROBABILITY) { dropItem(new FirstAid(gp)); }
        if (i >= Tank.MEDIUM_LOW_PROBABILITY && i < Tank.MEDIUM_HIGH_PROBABILITY) { dropItem(new Heart(gp)); }
        if (i >= Tank.MEDIUM_HIGH_PROBABILITY && i < Tank.HIGH_PROBABILITY) { dropItem(new Mana(gp)); }
    }
}
