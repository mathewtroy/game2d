package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.GamePanel;

import java.util.Random;

public class NPC_OldMan extends  Entity {

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");

        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
    }

    public void setDialogue() {

        dialogues[0] = "Hello, mister";
        dialogues[1] = "Welcome to FELL (HELL)";
        dialogues[2] = "I am so tired, man. \nI have semester project from PJV";
        dialogues[3] = "Good luck, Iwan";

    }

    public void setAction() {

        actionLockCounter ++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100)+1 ; // pick up a number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }

            if (i > 25 && i <= 50) {
                direction = "down";
            }

            if (i > 50 && i <= 75) {
                direction = "left";
            }

            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void speak() {

        if (dialogues[dialogueIndex] == null) {

            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction ="down";
                break;

            case "down":
                direction ="up";
                break;

            case "left":
                direction ="right";
                break;

            case "right":
                direction ="left";
                break;
        }
    }
}
