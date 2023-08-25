package cz.cvut.fel.pjv.monster;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.object.*;

import java.util.Random;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.monster.MON_Green.*;

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
     *
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
     *
     */
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

    /**
     *
     */
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
        onPath = true;
    }

    /**
     *
     */
    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(HIGH_PROBABILITY)+1;

        // SET THE MONSTER DROP
        if (i < MEDIUM_LOW_PROBABILITY) { dropItem(new OBJ_Potion_Red(gp)); }

        if (i >= MEDIUM_LOW_PROBABILITY && i < MEDIUM_HIGH_PROBABILITY) { dropItem(new OBJ_Heart(gp)); }

        if (i >= MEDIUM_HIGH_PROBABILITY && i < HIGH_PROBABILITY) { dropItem(new OBJ_ManaCrystal(gp)); }

    }
}
