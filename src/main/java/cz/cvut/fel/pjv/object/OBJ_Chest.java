package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;

import static cz.cvut.fel.pjv.Sound.SOUND_THREE;

public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened =false;

    public OBJ_Chest(GamePanel gp, Entity loot) {

        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";

        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collisionOn = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public void interact() {
        gp.gameState = gp.dialogueState;

        if (!opened) {
            gp.playSE(SOUND_THREE);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");

            if (gp.player.inventory.size() == gp.player.maxInventorySize) {
                sb.append("\n... But you cannot carry any more!");
            }
            else {
                sb.append("\n You obtain the " + loot.name + "!");
                gp.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }

            gp.ui.currentDialogue = sb.toString();
        }

        else {
            gp.ui.currentDialogue = "It is empty";
        }
    }

}
