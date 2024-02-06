package cz.cvut.fel.pjv.model.object;

import cz.cvut.fel.pjv.view.GamePanel;
import cz.cvut.fel.pjv.model.entity.Entity;

import java.util.logging.Logger;

public class OBJ_Shield_Blue extends Entity {

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    private static final String LOGGER_MESSAGE_SHIELD_BLUE = "Missing image of the SHIELD BLUE";
    public static final String objName = "Blue Shield";

    GamePanel gp;

    public  OBJ_Shield_Blue (GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_shield;
        setName(objName);
        defenseValue = 3;
        description = "[" + name + "]\nA new iron shield.";
        price = 10;
        setupShieldBlueImage();
    }

    /**
     * Sets up the image for the blue shield object.
     */
    private void setupShieldBlueImage() {
        try {
            down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            logger.warning(LOGGER_MESSAGE_SHIELD_BLUE);
        }
    }
}
