package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GameConstants;
import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class Heart extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HEART = "Missing image of the HEART";
    private static final String HEART_FULL_PATH = "/objects/heart_full";
    private static final String HEART_HALF_PATH = "/objects/heart_half";
    private static final String HEART_BLANK_PATH = "/objects/heart_blank";
    private static final String LIFE_PLUS_PREFIX = "Life +";
    public static final String objName = "Heart";

    GamePanel gp;

    public Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        setName(objName);
        value = 2;
        setupHeartImage();
    }

    /**
     * Sets up the image for the heart object.
     */
    private void setupHeartImage() {
        try {
            down1 = setup(HEART_FULL_PATH, gp.tileSize, gp.tileSize);
            image = setup(HEART_FULL_PATH, gp.tileSize, gp.tileSize);
            image2 = setup(HEART_HALF_PATH, gp.tileSize, gp.tileSize);
            image3 = setup(HEART_BLANK_PATH, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_HEART);
        }
    }

    /**
     * Uses the First Aid item on the specified entity.
     *
     * @param entity The entity on which the First Aid is used.
     * @return True if the First Aid was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.playSE(GameConstants.SOUND_TWO);
        gp.ui.addMessage(LIFE_PLUS_PREFIX + value) ;
        entity.life += value;
        return true;
    }
}
