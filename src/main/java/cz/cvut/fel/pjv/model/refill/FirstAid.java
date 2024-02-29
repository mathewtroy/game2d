package cz.cvut.fel.pjv.model.refill;

import cz.cvut.fel.pjv.model.object.Chest;
import cz.cvut.fel.pjv.model.object.Key;
import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;
import cz.cvut.fel.pjv.view.GameState;

import java.util.logging.Logger;

public class FirstAid extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_FIRST_AID = "Missing image of the FIRST AID";
    private static final String FIRST_AID_DESCRIPTION = "[First Aid]\nHeals your life by ";
    private static final String FIRST_AID_PATH = "/refill/first_aid";
    private static final String LIFE_RECOVERY_MESSAGE = "Life has been recovered by ";
    public static final String NEW_LINE = "\n";
    public static final String objName = "First Aid";

    GamePanel gp;

    public FirstAid(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        setName(objName);
        value = 5;
        description = FIRST_AID_DESCRIPTION + value + Chest.EXCLAMATION_MARK;
        price = 3;
        stackable = true;
        setupFirstAidImage();
    }

    /**
     * Sets up the image for the First aid object.
     */
    private void setupFirstAidImage() {
        try {
            down1 = setup(FIRST_AID_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_FIRST_AID);
        }
    }

    /**
     * Uses the First Aid to restore life to the player.
     *
     * @param entity The entity (usually the player) that uses the First Aid.
     * @return True if the First Aid was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.gameState = GameState.DIALOGUE;

        gp.ui.currentDialogue = Key.USE_MESSAGE + name + Chest.EXCLAMATION_MARK +
                NEW_LINE + LIFE_RECOVERY_MESSAGE + value + Chest.EXCLAMATION_MARK;
        entity.life += value;
        gp.playSE(GameConstants.SOUND_THREE);
        return true;
    }
}
