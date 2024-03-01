package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;

public class Chest extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_CHEST = "Missing image of the CHEST";
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
    public static final String EXCLAMATION_MARK = "!";
    public static final String objName = "Chest";

    GamePanel gp;

    public Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        setName(objName);
        collisionOn = true;
        solidArea.x = SOLID_X;
        solidArea.y = SOLID_Y;
        solidArea.width = SOLID_WIDTH;
        solidArea.height = SOLID_HEIGHT;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setupChestImage();
    }

    /**
     * Sets up the image for the chest object.
     */
    private void setupChestImage() {
        try {
            image = setup(CHEST_IMAGE_PATH, gp.tileSize, gp.tileSize);
            image2 = setup(CHEST_OPENED_IMAGE_PATH, gp.tileSize, gp.tileSize);
            down1 = image;
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_CHEST);
        }
    }

    /**
     * Sets the loot contained within the chest.
     *
     * @param loot The entity representing the loot to be set within the chest.
     */
    public void setLoot(Entity loot) {
        this.loot = loot;
    }

    /**
     * Handles the interaction with the chest.
     * When the chest is interacted with, it may be opened, and the player can obtain loot.
     */
    public void interact() {
        gp.gameState = GameState.DIALOGUE;
        if (!opened) { openChest(); }
        else { gp.ui.currentDialogue = EMPTY_CHEST_MESSAGE; }
    }

    /**
     * Opens the chest and handles obtaining loot.
     * If the player cannot carry the loot, it displays a message accordingly.
     */
    private void openChest() {
        gp.playSE(GameConstants.SOUND_THIRTEEN);
        StringBuilder sb = new StringBuilder();
        sb.append(OPEN_CHEST_MESSAGE).append(loot.name).append(EXCLAMATION_MARK);

        if (!gp.player.canObtainItem(loot)) {sb.append(CANNOT_CARRY_MESSAGE); }
        else {
            sb.append(OBTAIN_MESSAGE).append(loot.name).append(EXCLAMATION_MARK);
            down1 = image2;
            opened = true;
        }
        gp.ui.currentDialogue = sb.toString();
    }
}
