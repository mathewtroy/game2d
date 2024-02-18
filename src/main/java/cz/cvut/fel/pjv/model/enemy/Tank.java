package cz.cvut.fel.pjv.model.enemy;

import cz.cvut.fel.pjv.model.weapon.Bomb;
import cz.cvut.fel.pjv.model.object.Coin_Gold;
import cz.cvut.fel.pjv.model.object.Heart;
import cz.cvut.fel.pjv.model.object.ManaCrystal;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.Random;
import java.util.logging.Logger;

public class Tank extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_TANK = "Missing image of the TANK";
    private static final String enemyName = "Tank";


    // Probability Constants
    public static final int LOW_PROBABILITY = 25;
    public static final int MEDIUM_LOW_PROBABILITY = 50;
    public static final int MEDIUM_HIGH_PROBABILITY = 75;
    public static final int HIGH_PROBABILITY = 100;

    // Tile Distances
    public static final int CLOSE_DISTANCE = 5;
    public static final int FAR_DISTANCE = 7;

    // Shot Probability
    public static final int SHOT_PROBABILITY = 97;
    public static final int SHOT_COUNTER_MAX = 30;

    GamePanel gp;

    public Tank(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_enemy;
        setName(enemyName);
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 15;   // added more life
        life = maxLife;
        attack = 5;
        defense = 0;
        setExp(2);
        projectile = new Bomb(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setupTankImage();
    }

    /**
     * Sets up the images for the Tank.
     */
    private void setupTankImage() {
        try {
            up1 = setup("/enemy/tank_up", gp.tileSize, gp.tileSize);
            up2 = setup("/enemy/tank_up", gp.tileSize, gp.tileSize);
            down1 = setup("/enemy/tank_down", gp.tileSize, gp.tileSize);
            down2 = setup("/enemy/tank_down", gp.tileSize, gp.tileSize);
            left1 = setup("/enemy/tank_left", gp.tileSize, gp.tileSize);
            left2 = setup("/enemy/tank_left", gp.tileSize, gp.tileSize);
            right1 = setup("/enemy/tank_right", gp.tileSize, gp.tileSize);
            right2 = setup("/enemy/tank_right", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_TANK);
        }
    }

    /**
     * Sets the action and behavior for the Tank.
     */
    public void setAction () {
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < CLOSE_DISTANCE) {
            handleRandomPathSelection();
        }

        if (onPath && tileDistance > FAR_DISTANCE) {
            handleRandomPathSelection();
        }

        if (onPath) {
            handlePathAction();
        } else {
            handleRandomDirection();
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
        int i = new Random().nextInt(HIGH_PROBABILITY)+1;

        // SET THE TANK DROP
        if (i < MEDIUM_LOW_PROBABILITY) { dropItem(new Coin_Gold(gp)); }

        if (i >= MEDIUM_LOW_PROBABILITY && i < MEDIUM_HIGH_PROBABILITY) { dropItem(new Heart(gp)); }

        if (i >= MEDIUM_HIGH_PROBABILITY && i < HIGH_PROBABILITY) { dropItem(new ManaCrystal(gp)); }
    }

    /**
     * Handles the selection of a random path
     */
    private void handleRandomPathSelection() {
        int i = new Random().nextInt(HIGH_PROBABILITY) + 1;
        if (i > MEDIUM_LOW_PROBABILITY) {
            onPath = true;
        }
    }

    /**
     * Handles the action when the enemy is on a path
     */
    private void handlePathAction() {
        int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
        searchPath(goalCol, goalRow);

        int i = new Random().nextInt(HIGH_PROBABILITY) + 1;
        if (i > SHOT_PROBABILITY && (!projectile.alive) && shotAvailableCounter == SHOT_COUNTER_MAX) {
            shootProjectile();
        }
    }

    /**
     * Handles the selection of a random direction when not on a path
     */
    private void handleRandomDirection() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(HIGH_PROBABILITY) + 1; // pick up a number from 1 to 100

            if (i <= LOW_PROBABILITY) { direction = "up"; }
            if (i > LOW_PROBABILITY && i <= MEDIUM_LOW_PROBABILITY) { direction = "down"; }
            if (i > MEDIUM_LOW_PROBABILITY && i <= MEDIUM_HIGH_PROBABILITY) { direction = "left"; }
            if (i > MEDIUM_HIGH_PROBABILITY) { direction = "right"; }

            actionLockCounter = 0;
        }
    }

    /**
     * Shoots a projectile
     */
    private void shootProjectile() {
        projectile.set(worldX, worldY, direction, true, this);

        // CHECK VACANCY
        for (int j = 0; j < gp.projectile[1].length; j++) {
            if (gp.projectile[gp.currentMap][j] == null) {
                gp.projectile[gp.currentMap][j] = projectile;
                break;
            }
        }
        shotAvailableCounter = 0;
    }
}
