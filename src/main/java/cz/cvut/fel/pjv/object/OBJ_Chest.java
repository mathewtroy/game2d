package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_THIRTEEN;

public class OBJ_Chest extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_CHEST = "Missing image of the CHEST";
    public static final String objName = "Chest";

    private static final int SOLID_X = 4;
    private static final int SOLID_Y = 16;
    private static final int SOLID_WIDTH = 40;
    private static final int SOLID_HEIGHT = 32;

    private static final String CHEST_IMAGE_PATH = "/objects/chest";
    private static final String CHEST_OPENED_IMAGE_PATH = "/objects/chest_opened";

    private static final String OPEN_CHEST_MESSAGE = "You open the chest and find a ";
    private static final String CANNOT_CARRY_MESSAGE = "\n... But you cannot carry any more!";
    private static final String OBTAIN_MESSAGE = "\n You obtain the ";
    private static final String EMPTY_CHEST_MESSAGE = "It is empty";


    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        collisionOn = true;

        solidArea.x = SOLID_X;
        solidArea.y = SOLID_Y;
        solidArea.width = SOLID_WIDTH;
        solidArea.height = SOLID_HEIGHT;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setupChestImage();
    }

    private void setupChestImage() {
        try {
            image = setup(CHEST_IMAGE_PATH, gp.tileSize, gp.tileSize);
            image2 = setup(CHEST_OPENED_IMAGE_PATH, gp.tileSize, gp.tileSize);
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
            gp.playSE(SOUND_THIRTEEN);

            StringBuilder sb = new StringBuilder();
            sb.append(OPEN_CHEST_MESSAGE).append(loot.name).append("!");

            if (!gp.player.canObtainItem(loot)) {
                sb.append(CANNOT_CARRY_MESSAGE);
            }
            else {
                sb.append(OBTAIN_MESSAGE).append(loot.name).append("!");
                down1 = image2;
                opened = true;
            }

            gp.ui.currentDialogue = sb.toString();
        }

        else {
            gp.ui.currentDialogue = EMPTY_CHEST_MESSAGE;
        }
    }
}
