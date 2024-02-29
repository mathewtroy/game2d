package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Boots extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_BOOTS = "Missing image of the BOOTS";
    public static final String objName = "Boots";

    GamePanel gp;

    public Boots(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        value = 2;
        setName(objName);
        setupBootsImage();
    }

    /**
     * Sets up the image for the boots object.
     */
    private void setupBootsImage() {
        try {
            down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_BOOTS);
        }
    }

    /**
     * Uses the boots object, providing a speed boost to the player.
     * Plays a sound effect and adds a message to the user interface.
     *
     * @param entity The entity (typically the player) using the boots.
     * @return True if the boots were used successfully, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.playSE(GameConstants.SOUND_ONE);
        gp.ui.addMessage("Coin + " + value);
        gp.player.speed += value;
        return true;
    }
}
