package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.model.CollisionChecker.MAX_COST;
import static cz.cvut.fel.pjv.model.Sound.SOUND_THREE;


public class Key extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_KEY = "Missing image of the KEY";
    public static final String objName = "Key";

    GamePanel gp;

    public Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        setName(objName);
        description = "[" + name + "]\nAn old key.\nMade in China.";
        price = 10;
        stackable = true;
        setupKeyImage();
    }

    /**
     * Sets up the image for the key object.
     */
    private void setupKeyImage() {
        try {
            down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
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

        if (objIndex != MAX_COST) {
            gp.ui.currentDialogue = "You use the " + name + " and open the door";
            gp.playSE(SOUND_THREE);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "What are you doing man ??";
            return false;
        }
    }
}
