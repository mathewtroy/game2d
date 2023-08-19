package cz.cvut.fel.pjv.object;

import cz.cvut.fel.pjv.GamePanel;
import cz.cvut.fel.pjv.entity.Entity;

import java.util.logging.Logger;

import static cz.cvut.fel.pjv.Sound.SOUND_TWO;


public class OBJ_Heart extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_HEART = "Missing image of the HEART";
    public static final String objName = "Heart";

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        value = 2;

        setupHeartImage();
    }

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


    public boolean use(Entity entity) {
        gp.playSE(SOUND_TWO);
        gp.ui.addMessage("Life +" + value) ;
        entity.life += value;

        return true;
    }
}
