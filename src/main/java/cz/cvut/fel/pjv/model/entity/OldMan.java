package cz.cvut.fel.pjv.model.entity;

import cz.cvut.fel.pjv.controller.MapConstants;
import cz.cvut.fel.pjv.view.GamePanel;

import java.util.Random;

import static cz.cvut.fel.pjv.model.enemy.Tank.*;

public class OldMan extends  Entity {

    private static final String UP1_IMAGE_PATH = "/npc/oldman_up_1";
    private static final String UP2_IMAGE_PATH = "/npc/oldman_up_2";
    private static final String DOWN1_IMAGE_PATH = "/npc/oldman_down_1";
    private static final String DOWN2_IMAGE_PATH = "/npc/oldman_down_2";
    private static final String RIGHT1_IMAGE_PATH = "/npc/oldman_right_1";
    private static final String RIGHT2_IMAGE_PATH = "/npc/oldman_right_2";
    private static final String LEFT1_IMAGE_PATH = "/npc/oldman_left_1";
    private static final String LEFT2_IMAGE_PATH = "/npc/oldman_left_2";

    private static final String DIALOGUE_1 = "Hello, Iwan";
    private static final String DIALOGUE_2 = "Welcome to FEL (HELL)";
    private static final String DIALOGUE_3 = "I am so tired, man. \nI have semester project from PJV";
    private static final String DIALOGUE_4 = "Good luck, Iwan";

    public OldMan(GamePanel gp) {

        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    /**
     * Loads and sets the images for the old man's different directions.
     */
    private void getImage() {

        up1 = setup(UP1_IMAGE_PATH, gp.tileSize, gp.tileSize);
        up2 = setup(UP2_IMAGE_PATH, gp.tileSize, gp.tileSize);
        down1 = setup(DOWN1_IMAGE_PATH, gp.tileSize, gp.tileSize);
        down2 = setup(DOWN2_IMAGE_PATH, gp.tileSize, gp.tileSize);

        right1 = setup(RIGHT1_IMAGE_PATH, gp.tileSize, gp.tileSize);
        right2 = setup(RIGHT2_IMAGE_PATH, gp.tileSize, gp.tileSize);
        left1 = setup(LEFT1_IMAGE_PATH, gp.tileSize, gp.tileSize);
        left2 = setup(LEFT2_IMAGE_PATH, gp.tileSize, gp.tileSize);
    }

    /**
     * Sets the dialogue options for the old man.
     */
    private void setDialogue() {
        dialogues[0] = DIALOGUE_1;
        dialogues[1] = DIALOGUE_2;
        dialogues[2] = DIALOGUE_3;
        dialogues[3] = DIALOGUE_4;
    }

    /**
     * Defines the actions of the old man NPC.
     * If not on a predefined path, the old man will randomly change directions after a certain time interval.
     */
    public void setAction() {

        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter ++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(HIGH_PROBABILITY)+1 ; // pick up a number from 1 to 100
                if (i <= LOW_PROBABILITY) { direction = MapConstants.UP; }
                if (i > LOW_PROBABILITY && i <= MEDIUM_LOW_PROBABILITY) { direction = MapConstants.DOWN; }
                if (i > MEDIUM_LOW_PROBABILITY && i <= MEDIUM_HIGH_PROBABILITY) { direction = MapConstants.LEFT; }
                if (i > MEDIUM_HIGH_PROBABILITY) { direction = MapConstants.RIGHT; }
                actionLockCounter = 0;
            }
        }
    }

    /**
     * Initiates a conversation with the old man.
     * Sets the NPC on a predefined path.
     */
    public void speak() {
        // Do this character specific stuff
        super.speak();
        onPath = true;
    }
}
