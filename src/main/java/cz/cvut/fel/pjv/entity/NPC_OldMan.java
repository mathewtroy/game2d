package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;

import java.util.Random;

import static cz.cvut.fel.pjv.monster.MON_GreenSlime.*;

public class NPC_OldMan extends  Entity {

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);

        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Hello, mister";
        dialogues[1] = "Welcome to FELL (HELL)";
        dialogues[2] = "I am so tired, man. \nI have semester project from PJV";
        dialogues[3] = "Good luck, Iwan";

    }

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

                if (i <= LOW_PROBABILITY) { direction = "up"; }
                if (i > LOW_PROBABILITY && i <= MEDIUM_LOW_PROBABILITY) { direction = "down"; }
                if (i > MEDIUM_LOW_PROBABILITY && i <= MEDIUM_HIGH_PROBABILITY) { direction = "left"; }
                if (i > MEDIUM_HIGH_PROBABILITY) { direction = "right"; }

                actionLockCounter = 0;
            }
        }



    }

    public void speak() {
        // Do this character specific stuff
        super.speak();

        onPath = true;
    }

}
