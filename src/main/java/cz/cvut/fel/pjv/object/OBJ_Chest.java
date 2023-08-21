package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_THREE;

public class OBJ_Chest extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_CHEST = "Missing image of the CHEST";
    public static final String objName = "Chest";

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        collisionOn = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setupChestImage();
    }

    private void setupChestImage() {
        try {
            image = setup("/objects/chest", gp.tileSize, gp.tileSize);
            image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
            down1 = image;
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_CHEST);
        }
    }

    public void setLoot(Entity loot) {
        this.loot = loot;
    }

    public void interact() {
        gp.gameState = GamePanel.GameState.DIALOGUE;

        if (!opened) {
            gp.playSE(SOUND_THREE);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");

            if (!gp.player.canObtainItem(loot)) {
                sb.append("\n... But you cannot carry any more!");
            }
            else {
                sb.append("\n You obtain the " + loot.name + "!");
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
