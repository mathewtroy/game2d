package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Mana extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_MANA = "Missing image of the MANA";
    private static final String MANA_PLUS_PREFIX = "Mana +";
    private static final String FULL_BULLET_PATH = "/objects/bullet_full";
    private static final String BLANK_BULLET_PATH = "/objects/bullet_blank";
    public static final String objName = "Mana Crystal";

    GamePanel gp;

    public Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        setName(objName);
        value = 1;
        price = 5;
        setupManaImage();
    }

    /**
     * Sets up the image for the mana crystal object.
     */
    private void setupManaImage() {
        try {
            down1 = setup(FULL_BULLET_PATH, gp.tileSize, gp.tileSize);
            image = setup(FULL_BULLET_PATH, gp.tileSize, gp.tileSize);
            image2 = setup(BLANK_BULLET_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_MANA);
        }
    }

    /**
     * Uses the Mana Crystal to restore mana to the player.
     *
     * @param entity The entity (usually the player) that uses the Mana Crystal.
     * @return True if the Mana Crystal was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.playSE(GameConstants.SOUND_TWO);
        gp.ui.addMessage(MANA_PLUS_PREFIX + value) ;
        entity.mana += value;
        return true;
    }
}
