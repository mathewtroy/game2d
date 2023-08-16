package cz.cvut.fel.pjv.monster;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.object.*;

import java.util.Random;

public class MON_Green extends Entity {

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

    public MON_Green (GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Green monster";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 15;   // added more life
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public  void getImage() {

        up1 = setup("/monster/green_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/green_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/green_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/green_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/green_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/green_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/green_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/green_down_2", gp.tileSize, gp.tileSize);

    }

    public void setAction () {

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < CLOSE_DISTANCE) {
            int i = new Random().nextInt(HIGH_PROBABILITY)+1;
            if (i > MEDIUM_LOW_PROBABILITY) {
                onPath = true;
            }
        }

        if (onPath && tileDistance > FAR_DISTANCE) { onPath = true; }

        if (onPath) {

            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(HIGH_PROBABILITY)+1;
            if (i > SHOT_PROBABILITY && (!projectile.alive)
                    && shotAvailableCounter == SHOT_COUNTER_MAX) {

                projectile.set(worldX, worldY, direction,true, this);

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
        else {

            actionLockCounter++;

            if (actionLockCounter == 120) {

                Random random = new Random();
                int i = random.nextInt(HIGH_PROBABILITY)+1 ; // pick up a number from 1 to 100

                if (i <= LOW_PROBABILITY) { direction = "up"; }
                if (i > LOW_PROBABILITY && i <= MEDIUM_LOW_PROBABILITY) { direction = "down"; }
                if (i > MEDIUM_LOW_PROBABILITY && i <= MEDIUM_HIGH_PROBABILITY) { direction = "left"; }
                if (i > MEDIUM_HIGH_PROBABILITY) { direction = "right"; }

                actionLockCounter = 0;
            }
        }

    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(HIGH_PROBABILITY)+1;

        // SET THE MONSTER DROP
        if (i < MEDIUM_LOW_PROBABILITY) { dropItem(new OBJ_Coin_Bronze(gp)); }

        if (i >= MEDIUM_LOW_PROBABILITY && i < MEDIUM_HIGH_PROBABILITY) { dropItem(new OBJ_Heart(gp)); }

        if (i >= MEDIUM_HIGH_PROBABILITY && i < HIGH_PROBABILITY) { dropItem(new OBJ_ManaCrystal(gp)); }

    }
}
