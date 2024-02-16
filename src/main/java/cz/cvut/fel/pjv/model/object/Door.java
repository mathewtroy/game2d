package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;


public class Door extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_DOOR = "Missing image of the DOOR";
    public static final String objName = "Door";

    GamePanel gp;

    public Door(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        setName(objName);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setupDoorImage();
    }

    /**
     * Sets up the image for the door object.
     */
    private void setupDoorImage() {
        try {
            down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_DOOR);
        }
    }

    /**
     * Handles the interaction with the door.
     * Displays a message indicating that a key is needed to open the door.
     */
    public void interact() {
        gp.gameState = GameState.DIALOGUE;
        gp.ui.currentDialogue = "You need a key to open the door";
    }
}
