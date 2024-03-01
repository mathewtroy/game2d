package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;

public class Key extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_KEY = "Missing image of the KEY";
    private static final String KEY_PATH = "/objects/key";
    private static final String OLD_KEY_DESCRIPTION = "]\nAn old key.\nMade in China.";
    private static final String OPEN_DOOR_MESSAGE = " and open the door";
    private static final String WARN_MESSAGE = "What are you doing man??";
    public static final String USE_MESSAGE = "You use the ";
    public static final String objName = "Key";
    public static final String SQUARE_BRACKET = "[";

    GamePanel gp;

    public Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        setName(objName);
        description = SQUARE_BRACKET + name + OLD_KEY_DESCRIPTION;
        price = 10;
        stackable = true;
        setupKeyImage();
    }

    /**
     * Sets up the image for the key object.
     */
    private void setupKeyImage() {
        try {
            down1 = setup(KEY_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_KEY);
        }
    }

    /**
     * Uses the key item to open a door when interacting with it.
     *
     * @param entity The entity that uses the key.
     * @return True if the key was successfully used to open a door, false otherwise.
     */
    public boolean use(Entity entity) {

        gp.gameState = GameState.DIALOGUE;
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != GameConstants.MAX_COST) {
            gp.ui.currentDialogue = USE_MESSAGE + name + OPEN_DOOR_MESSAGE;
            gp.playSE(GameConstants.SOUND_THREE);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = WARN_MESSAGE;
            return false;
        }
    }
}
