package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.model.Sound.SOUND_TWO;


public class Heart extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HEART = "Missing image of the HEART";
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
            down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
            image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
            image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
            image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_HEART);
        }
    }

    /**
     * Uses the Life Potion item on the specified entity.
     *
     * @param entity The entity on which the Life Potion is used.
     * @return True if the Life Potion was successfully used, false otherwise.
     */
    public boolean use(Entity entity) {
        gp.playSE(SOUND_TWO);
        gp.ui.addMessage("Life +" + value) ;
        entity.life += value;
        return true;
    }
}
